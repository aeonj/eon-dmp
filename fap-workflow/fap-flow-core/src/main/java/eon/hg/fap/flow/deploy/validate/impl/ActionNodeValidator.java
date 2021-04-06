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
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author Jacky
 * @since 2013年9月15日
 */
public class ActionNodeValidator extends NodeValidator {

	public void validate(Element element, List<String> errors,List<String> nodeNames) {
		super.validate(element, errors,nodeNames);
		String handlerBean=element.getAttribute("handler-bean");
		if(StrUtil.isEmpty(handlerBean)){
			errors.add("动作节点必须要定义实现类Bean");
		}
	}

	public boolean support(Element element) {
		return element.getNodeName().equals("action");
	}
	
	public String getNodeName() {
		return "动作";
	}
}
