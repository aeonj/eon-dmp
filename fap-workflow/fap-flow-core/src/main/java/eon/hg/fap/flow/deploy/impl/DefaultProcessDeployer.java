/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package eon.hg.fap.flow.deploy.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.flow.command.CommandService;
import eon.hg.fap.flow.command.impl.DeployProcessCommand;
import eon.hg.fap.flow.command.impl.DeployProcessResourceCommand;
import eon.hg.fap.flow.deploy.ProcessDeployer;
import eon.hg.fap.flow.deploy.parse.impl.ProcessParser;
import eon.hg.fap.flow.deploy.validate.ProcessValidateException;
import eon.hg.fap.flow.deploy.validate.impl.ProcessValidator;
import eon.hg.fap.flow.model.ProcessDefinition;
import eon.hg.fap.flow.utils.IDGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sun.nio.ch.IOUtil;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Jacky.gao
 * @since 2013年8月3日
 */
public class DefaultProcessDeployer implements ProcessDeployer{
	private DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	private ProcessValidator processValidator;
	private CommandService commandService;
	public ProcessDefinition deploy(InputStream inputStream) {
		ProcessDefinition process=null;
		long processId=IDGenerator.getInstance().nextId();
		if(inputStream instanceof ZipInputStream){
			//表示通过zip打包上传的流程模版文件，其中可能包含流程图片文件(png格式)
			ZipInputStream zipInputStream=(ZipInputStream)inputStream;
			process = deployZip(zipInputStream,processId);
		}else{
			//认为是一个以uflo.xml结尾的普通的流程模版文件
			process = deployFile(inputStream,null,processId,false);
		}
		return process;
	}
	
	public ProcessDefinition deploy(InputStream inputStream,int version, long processId) {
		ProcessDefinition process=deployFile(inputStream,version,processId,true);
		return process;
	}

	private ProcessDefinition deployFile(InputStream inputStream,Integer version,long processId,boolean update) {
		ProcessDefinition process=null;
		try{
			byte[] bytes= IoUtil.readBytes(inputStream);
			validateProcess(new ByteArrayInputStream(bytes));
			process=ProcessParser.parseProcess(bytes,processId,update);
			process.setCreateDate(new Date());
			if(update){
				process.setVersion(version);				
			}
			commandService.executeCommand(new DeployProcessCommand(process,update));
			commandService.executeCommand(new DeployProcessResourceCommand(bytes,process.getName()+PROCESS_EXTENSION_NAME,processId,update));
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}finally{
			IoUtil.close(inputStream);
		}
		return process;
	}
	
	
	private ProcessDefinition deployZip(ZipInputStream zipInputStream,long processId) {
		ProcessDefinition process=null;
		try {
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				String entryName = zipEntry.getName();
				if(entryName.endsWith(PROCESS_EXTENSION_NAME)){
					byte[] bytes = IoUtil.readBytes(zipInputStream);
					validateProcess(new ByteArrayInputStream(bytes));
					process=ProcessParser.parseProcess(bytes,processId,false);
					commandService.executeCommand(new DeployProcessCommand(process,false));
					commandService.executeCommand(new DeployProcessResourceCommand(bytes,entryName,processId,false));
				}else if(entryName.endsWith(PROCESSIMG_EXTENSION_NAME)){
					byte[] bytes = IoUtil.readBytes(zipInputStream);
					commandService.executeCommand(new DeployProcessResourceCommand(bytes,entryName,processId,false));
				}
				zipEntry = zipInputStream.getNextEntry();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IoUtil.close(zipInputStream);
		}
		return process;
	}

	private void validateProcess(InputStream inputStream) {
		StringBuffer errorInfo=new StringBuffer();
        try {
        	Document document = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
        	List<String> errors=new ArrayList<String>();
        	List<String> nodeNames=new ArrayList<String>();
        	Element element=document.getDocumentElement();
        	if(processValidator.support(element)){
        		processValidator.validate(element, errors, nodeNames);
        		if(errors.size()>0){
        			for(int i=0;i<errors.size();i++){
        				errorInfo.append((i+1)+"."+errors.get(i)+"\r\r");
        			}
        		}
        	}else{
        		errorInfo.append("当前XML文件不是一个合法的FLOW流程模版文件");
        	}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
       String msg=errorInfo.toString();
       if(StrUtil.isNotEmpty(msg)){
    	   throw new ProcessValidateException(msg);
       }
	}
	
	public void setProcessValidator(ProcessValidator processValidator) {
		this.processValidator = processValidator;
	}

	public void setCommandService(CommandService commandService) {
		this.commandService = commandService;
	}
}
