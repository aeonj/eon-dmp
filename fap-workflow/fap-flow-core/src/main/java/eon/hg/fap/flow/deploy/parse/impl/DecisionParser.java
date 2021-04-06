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
import eon.hg.fap.flow.process.node.DecisionNode;
import eon.hg.fap.flow.process.node.DecisionType;
import org.dom4j.Element;

/**
 * @author Jacky.gao
 * @since 2013年8月13日
 */
public class DecisionParser extends AbstractParser {

	public Object parse(Element element, long processId, boolean parseChildren) {
		DecisionNode node=new DecisionNode();
		node.setProcessId(processId);
		parseNodeCommonInfo(element, node);
		node.setSequenceFlows(parseFlowElement(element,processId,parseChildren));
		node.setDecisionType(DecisionType.valueOf(element.attributeValue("decision-type")));
		if(node.getDecisionType().equals(DecisionType.Expression)){
			for(Object obj:element.elements()){
				if(!(obj instanceof Element)){
					continue;
				}
				Element ele=(Element)obj;
				if(ele.getName().equals("expression")){
					node.setExpression(ele.getText());
					break;
				}
			}
			if(StrUtil.isBlank(node.getExpression())){
				node.setExpression(element.attributeValue("expression"));
			}
		}
		node.setHandlerBean(unescape(element.attributeValue("handler-bean")));
		NodeDiagram diagram=parseDiagram(element);
		diagram.setIcon("/icons/decision.svg");
		diagram.setShapeType(ShapeType.Circle);
		diagram.setBorderWidth(1);
		node.setDiagram(diagram);
		return node;
	}

	public boolean support(Element element) {
		return element.getName().equals("decision");
	}

}
