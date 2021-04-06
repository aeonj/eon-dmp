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
package eon.hg.fap.flow.deploy.parse.impl;

import cn.hutool.core.util.StrUtil;
import eon.hg.fap.flow.deploy.parse.AbstractParser;
import eon.hg.fap.flow.diagram.NodeDiagram;
import eon.hg.fap.flow.diagram.ShapeType;
import eon.hg.fap.flow.process.node.SubprocessNode;
import eon.hg.fap.flow.process.node.SubprocessType;
import eon.hg.fap.flow.process.node.SubprocessVariable;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年8月13日
 */
public class SubprocessParser extends AbstractParser {

	public Object parse(Element element, long processId, boolean parseChildren) {
		SubprocessNode node=new SubprocessNode();
		node.setProcessId(processId);
		parseNodeCommonInfo(element, node);
		node.setSequenceFlows(parseFlowElement(element,processId,parseChildren));
		String type=element.attributeValue("subprocess-type");
		if(StrUtil.isNotEmpty(type)){
			node.setSubprocessType(SubprocessType.valueOf(type));			
		}
		String completeStartTask=element.attributeValue("complete-start-task");
		if(StrUtil.isNotBlank(completeStartTask)){
			node.setCompleteStartTask(Boolean.valueOf(completeStartTask));
		}
		node.setSubprocessId(unescape(element.attributeValue("subprocess-id")));
		node.setSubprocessKey(unescape(element.attributeValue("subprocess-key")));
		node.setSubprocessName(unescape(element.attributeValue("subprocess-name")));
		List<SubprocessVariable> inVars=new ArrayList<SubprocessVariable>();
		List<SubprocessVariable> outVars=new ArrayList<SubprocessVariable>();
		for(Object obj:element.elements()){
			if(!(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			if(ele.getName().equals("in-subprocess-variable")){
				SubprocessVariable var=new SubprocessVariable(unescape(ele.attributeValue("in-parameter-key")),unescape(ele.attributeValue("out-parameter-key")));
				inVars.add(var);
			}
			if(ele.getName().equals("out-subprocess-variable")){
				SubprocessVariable var=new SubprocessVariable(unescape(ele.attributeValue("in-parameter-key")),unescape(ele.attributeValue("out-parameter-key")));
				outVars.add(var);
			}
		}
		node.setInVariables(inVars);
		node.setOutVariables(outVars);
		NodeDiagram diagram=parseDiagram(element);
		diagram.setIcon("/icons/subprocess.svg");
		diagram.setShapeType(ShapeType.Rectangle);
		diagram.setBorderWidth(1);
		diagram.setBorderColor("3, 104, 154");
		diagram.setBackgroundColor("250, 250, 250");
		node.setDiagram(diagram);
		return node;
	}

	public boolean support(Element element) {
		return element.getName().equals("subprocess");
	}

}
