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

import eon.hg.fap.flow.deploy.parse.AbstractTaskParser;
import eon.hg.fap.flow.diagram.NodeDiagram;
import eon.hg.fap.flow.diagram.ShapeType;
import eon.hg.fap.flow.process.node.StartNode;
import org.dom4j.Element;

/**
 * @author Jacky.gao
 * @since 2013年7月30日
 */
public class StartParser extends AbstractTaskParser {

	public Object parse(Element element,long processId,boolean parseChildren) {
		StartNode node=new StartNode();
		node.setProcessId(processId);
		node.setTaskName(unescape(element.attributeValue("task-name")));
		node.setUrl(unescape(element.attributeValue("url")));
		node.setFormTemplate(unescape(element.attributeValue("form-template")));
		parseNodeCommonInfo(element, node);
		node.setSequenceFlows(parseFlowElement(element,processId,parseChildren));
		node.setFormElements(parseFormElements(element));
		node.setComponentAuthorities(parseComponentAuthorities(element));
		NodeDiagram diagram=parseDiagram(element);
		diagram.setIcon("/icons/start.svg");
		diagram.setShapeType(ShapeType.Circle);
		diagram.setBorderWidth(1);
		node.setDiagram(diagram);
		return node;
	}

	public boolean support(Element element) {
		return element.getName().equals("start");
	}
}
