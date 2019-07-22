package eon.hg.fap.web.manage.action;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.model.primary.Element;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.IBaseTreeService;
import eon.hg.fap.web.manage.op.ElementOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class DictionaryAction extends BizAction {
	@Autowired
	private ElementOP eleOp;
	@Autowired
	private IBaseTreeService baseTreeService;

	@RequestMapping("/ele_chk_tree.htm")
	public String ele_chk_tree(@RequestParam Map<String, Object> map, String node) throws Exception {
		Dto dto = new HashDto(map);
		dto.put("parent_id", node);
		if (CommUtil.isNotEmpty(dto.get("source"))) {
			if (CommUtil.isNotEmpty(dto.get("ispermission"))) {
				if (dto.getBoolean("ispermission")) {
					User user = SecurityUserHolder.getCurrentUser();
					if (user==null) {
						dto.put("belong_source", "-1");
					} else {
						dto.put("belong_source", user.getBelong_source());
					}
				}
			}
			boolean isDirect = false;
			isDirect = dto.getBoolean("isdirect").booleanValue();
			dto.remove("isdirect");
			if (CommUtil.isNotEmpty(dto.get("condition"))) {
				isDirect = true;
			}
			if (CommUtil.isNotEmpty(dto.get("isfulllevel"))) {
				if (!dto.getBoolean("isfulllevel")) {
					isDirect = true;
				}
			}
			List<Dto> list;
			Element ele = eleOp.getEleSource(dto.getString("source"));
			if (ele==null) {
				return "[]";
			}
			//只要要素设置了类名，必须使用类方式获取树
			boolean istable = false;
			if (CommUtil.isEmpty(ele.getClass_name())) {
				istable = true;
			} else {
				dto.put("class_name",ele.getClass_name());
			}
			boolean isChecked = dto.getString("selectmodel").equalsIgnoreCase("multiple") ;
			dto.put("table_name",ele.getEle_source());
			if (istable) {
				list = isDirect ? baseTreeService.getObject(dto) : baseTreeService.getCache(dto);
				if (!isDirect && isChecked && dto.get("checkids")!=null) {
					dto.put("values", dto.get("checkids"));
				}
			} else {
				list = eleOp.getObject(dto);
			}
			if (isChecked && dto.get("values")!=null) {
				eleOp.setCheckNodeList(list,dto.getString("values"));
			}
			String jsonString = JsonHandler.toJson(list);
			return jsonString;
		} else {
			return "[]";
		}
	}


	@RequestMapping("/eleinfo_ajax_id.htm")
	public String eleinfo_ajax_id(String source, String value) {
		Dto bd = this.eleOp.getBaseDtoById(source,value);
		return JsonHandler.toJson(bd);
	}
	
	@RequestMapping("/eleinfo_ajax_code.htm")
	public String eleinfo_ajax_code(String source, String value) {
		Dto bd = this.eleOp.getBaseDtoByCode(source,value);
		return JsonHandler.toJson(bd);
	}
	
}
