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
package eon.hg.fap.flow.deploy.validate.impl;

import cn.hutool.core.util.StrUtil;
import eon.hg.fap.flow.process.node.SubprocessType;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author Jacky
 * @since 2013年9月15日
 */
public class SubprocessNodeValidator extends NodeValidator {

	public void validate(Element element, List<String> errors,List<String> nodeNames) {
		super.validate(element, errors,nodeNames);
		String subprocessType=element.getAttribute("subprocess-type");
		if(StrUtil.isEmpty(subprocessType)){
			errors.add("子流程节点必须指定加载子流程方式");
		}else{
			SubprocessType type=SubprocessType.valueOf(subprocessType);
			if(type.equals(SubprocessType.Id) && StrUtil.isEmpty(element.getAttribute("subprocess-id"))){
				errors.add("子流程节点在指定加载子流程方式为子流程ID时，必须要设置具体的子流程ID");				
			}
			if(type.equals(SubprocessType.Name) && StrUtil.isEmpty(element.getAttribute("subprocess-name"))){
				errors.add("子流程节点在指定加载子流程方式为子流程名称时，必须要设置具体的子流程名称");				
			}
			if(type.equals(SubprocessType.Key) && StrUtil.isEmpty(element.getAttribute("subprocess-key"))){
				errors.add("子流程节点在指定加载子流程方式为子流程Key时，必须要设置具体的子流程Key");				
			}
		}
		
	}
	
	public boolean support(Element element) {
		return element.getNodeName().equals("subprocess");
	}

	public String getNodeName() {
		return "子流程";
	}
}
