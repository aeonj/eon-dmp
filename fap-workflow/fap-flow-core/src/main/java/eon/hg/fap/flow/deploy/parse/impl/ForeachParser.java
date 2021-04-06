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
import eon.hg.fap.flow.process.node.ForeachNode;
import eon.hg.fap.flow.process.node.ForeachType;
import org.dom4j.Element;

/**
 * @author Jacky.gao
 * @since 2013年8月13日
 */
public class ForeachParser extends AbstractParser {

	public Object parse(Element element, long processId, boolean parseChildren) {
		ForeachNode node=new ForeachNode();
		node.setProcessId(processId);
		parseNodeCommonInfo(element, node);
		node.setSequenceFlows(parseFlowElement(element,processId,parseChildren));
		String type=element.attributeValue("foreach-type");
		if(StrUtil.isNotEmpty(type)){
			node.setForeachType(ForeachType.valueOf(type));			
		}
		node.setVariable(unescape(element.attributeValue("var")));
		if(StrUtil.isNotBlank(element.attributeValue("process-variable"))){
			node.setProcessVariable(unescape(element.attributeValue("process-variable")));			
		}else{
			node.setProcessVariable(unescape(element.attributeValue("in")));						
		}
		node.setHandlerBean(unescape(element.attributeValue("handler-bean")));
		NodeDiagram diagram=parseDiagram(element);
		diagram.setIcon("/icons/foreach.svg");
		diagram.setShapeType(ShapeType.Circle);
		diagram.setBorderWidth(1);
		node.setDiagram(diagram);
		return node;
	}

	public boolean support(Element element) {
		return element.getName().equals("foreach");
	}

}
