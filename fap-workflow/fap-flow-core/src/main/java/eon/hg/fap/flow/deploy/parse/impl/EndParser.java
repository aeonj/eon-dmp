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
import eon.hg.fap.flow.process.node.EndNode;
import org.dom4j.Element;

/**
 * @author Jacky.gao
 * @since 2013年8月13日
 */
public class EndParser extends AbstractParser {

	public Object parse(Element element,long processId,boolean parseChildren) {
		EndNode node=new EndNode();
		node.setProcessId(processId);
		parseNodeCommonInfo(element, node);
		String terminate=element.attributeValue("terminate");
		if(StrUtil.isNotEmpty(terminate)){
			node.setTerminate(Boolean.valueOf(terminate));
		}
		NodeDiagram diagram=parseDiagram(element);
		if(node.isTerminate()){
			diagram.setIcon("/icons/end-terminate.svg");
		}else{
			diagram.setIcon("/icons/end.svg");
		}
		diagram.setShapeType(ShapeType.Circle);
		diagram.setBorderWidth(1);
		node.setDiagram(diagram);
		return node;
	}

	public boolean support(Element element) {
		return element.getName().equals("end");
	}
}
