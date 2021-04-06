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
import eon.hg.fap.flow.deploy.StringTools;
import eon.hg.fap.flow.deploy.parse.Parser;
import eon.hg.fap.flow.diagram.Point;
import eon.hg.fap.flow.diagram.SequenceFlowDiagram;
import eon.hg.fap.flow.process.flow.ConditionType;
import eon.hg.fap.flow.process.flow.SequenceFlowImpl;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年8月5日
 */
public class SequenceFlowParser implements Parser {

	public Object parse(Element element,long processId,boolean parseChildren) {
		SequenceFlowImpl flow=new SequenceFlowImpl();
		flow.setProcessId(processId);
		flow.setName(unescape(element.attributeValue("name")));
		flow.setToNode(unescape((element.attributeValue("to"))));
		String conditionType=element.attributeValue("condition-type");
		if(StrUtil.isNotEmpty(conditionType)){
			flow.setConditionType(ConditionType.valueOf(conditionType));			
			flow.setExpression(element.attributeValue("expression"));
			flow.setHandlerBean(element.attributeValue("handler-bean"));
		}
		flow.setDiagram(parseDiagram(element));
		String g=element.attributeValue("g");
		if(StrUtil.isNotBlank(g)){
			int pos=g.indexOf(":");
			if(pos>-1){
				g=g.substring(0,pos);
				g=g.replaceAll(";", ",");
			}else{
				g=null;
			}
		}
		flow.setG(g);
		return flow;
	}

	private SequenceFlowDiagram parseDiagram(Element element){
		SequenceFlowDiagram diagram=new SequenceFlowDiagram();
		diagram.setBorderColor("0,69,123");
		diagram.setFontColor("0,69,123");
		diagram.setBorderWidth(2);
		String name=element.attributeValue("name");
		diagram.setTo(element.attributeValue("to"));
		diagram.setName(name);
		String g=element.attributeValue("g");
		if(StrUtil.isEmpty(g))return diagram;
		String[] pointInfos=null;
		if(StrUtil.isNotEmpty(name)){
			String[] info=g.split(":");
			if(info.length==1){
				diagram.setLabelPosition(info[0]);				
				return diagram;
			}
			pointInfos=info[0].split(";");
			diagram.setLabelPosition(info[1]);				
		}else{
			String[] info=g.split(":");
			if(info.length==0){
				pointInfos=g.split(";");
			}else{
				pointInfos=info[0].split(";");
			}
			if(pointInfos.length==0){
				return diagram;
			}
		}
		diagram.setPoints(buildPoint(pointInfos));
		return diagram;
	}
	
	private List<Point> buildPoint(String[] info){
		List<Point> points=new ArrayList<Point>();
		for(String diagram:info){
			String[] d=diagram.split(",");
			if(StrUtil.isEmpty(d[0])){
				continue;
			}
			if(d.length>1){
				for(int i=0;i<d.length;i+=2){
					Point point=new Point();
					point.setX(Integer.valueOf(d[i]));
					point.setY(Integer.valueOf(d[i+1]));
					points.add(point);	
				}
			}
		}
		return points;
	}
	
	public boolean support(Element element) {
		return element.getName().equals("sequence-flow");
	}
	
	protected String unescape(String str){
		if(StrUtil.isEmpty(str))return str;
		//modify by eonook
		str= StringTools.escape(str);
		return StringTools.unescape(str);
	}
}
