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
import eon.hg.fap.flow.process.node.JoinNode;
import org.dom4j.Element;

/**
 * @author Jacky.gao
 * @since 2013年8月13日
 */
public class JoinParser extends AbstractParser {

	public Object parse(Element element, long processId, boolean parseChildren) {
		JoinNode node=new JoinNode();
		node.setProcessId(processId);
		parseNodeCommonInfo(element, node);
		node.setSequenceFlows(parseFlowElement(element,processId,parseChildren));
		String multiplicity=element.attributeValue("multiplicity");
		if(StrUtil.isNotEmpty(multiplicity)){
			int mul=Integer.parseInt(multiplicity);
			if(mul>0){
				node.setMultiplicity(mul);
			}
		}
		String percentMultiplicity=element.attributeValue("percent-multiplicity");
		if(StrUtil.isNotEmpty(percentMultiplicity)){
			int mul=Integer.parseInt(percentMultiplicity);
			if(mul>0){
				node.setPercentMultiplicity(mul);
			}
		}
		NodeDiagram diagram=parseDiagram(element);
		diagram.setIcon("/icons/join.svg");
		diagram.setShapeType(ShapeType.Circle);
		diagram.setBorderWidth(1);
		node.setDiagram(diagram);
		return node;
	}

	public boolean support(Element element) {
		return element.getName().equals("join");
	}

}
