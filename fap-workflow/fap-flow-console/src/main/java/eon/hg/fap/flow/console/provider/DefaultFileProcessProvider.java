package eon.hg.fap.flow.console.provider;

import cn.hutool.core.io.IoUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.*;

/**
 * @author Jacky.gao
 * @since 2017年7月12日
 */
public class DefaultFileProcessProvider implements ProcessProvider,ApplicationContextAware,InitializingBean {
	public String prefix="file:";
	private ApplicationContext applicationContext;
	private String fileStoreDir;
	private boolean disabled;
	@Override
	public InputStream loadProcess(String file) {
		if(file.startsWith(prefix)){
			file=file.substring(prefix.length(),file.length());
		}
		String fullPath=fileStoreDir+"/"+file;
		try {
			return new FileInputStream(fullPath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void saveProcess(String file, String content) {
		if(file.startsWith(prefix)){
			file=file.substring(prefix.length(),file.length());
		}
		String fullPath=fileStoreDir+"/"+file;
		FileOutputStream outStream=null;
		try{
			outStream=new FileOutputStream(new File(fullPath));
			IoUtil.writeUtf8(outStream,true,content);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public void deleteProcess(String file) {
		if(file.startsWith(prefix)){
			file=file.substring(prefix.length(),file.length());
		}
		String fullPath=fileStoreDir+"/"+file;
		File f=new File(fullPath);
		if(f.exists()){
			f.delete();
		}
	}

	@Override
	public List<ProcessFile> loadAllProcesses() {
		File file=new File(fileStoreDir);
		List<ProcessFile> list=new ArrayList<ProcessFile>();
		for(File f:file.listFiles()){
			Calendar calendar=Calendar.getInstance();
			calendar.setTimeInMillis(f.lastModified());
			list.add(new ProcessFile(f.getName(),calendar.getTime()));
		}
		Collections.sort(list, new Comparator<ProcessFile>(){
			@Override
			public int compare(ProcessFile f1, ProcessFile f2) {
				return f2.getUpdateDate().compareTo(f1.getUpdateDate());
			}
		});
		return list;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		File file=new File(fileStoreDir);
		if(file.exists()){
			return;
		}
		WebApplicationContext context=(WebApplicationContext)applicationContext;
		ServletContext servletContext=context.getServletContext();
		String basePath=servletContext.getRealPath("/");
		fileStoreDir=basePath+fileStoreDir;
		file=new File(fileStoreDir);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
	
	public void setFileStoreDir(String fileStoreDir) {
		this.fileStoreDir = fileStoreDir;
	}
	
	@Override
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public boolean support(String fileName) {
		return fileName.startsWith(prefix);
	}
	@Override
	public String getPrefix() {
		return prefix;
	}
	
	@Override
	public String getName() {
		return "默认文件系统";
	}
}
