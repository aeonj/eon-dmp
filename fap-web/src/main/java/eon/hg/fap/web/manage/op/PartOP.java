package eon.hg.fap.web.manage.op;

import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.db.model.primary.PartGroup;
import eon.hg.fap.db.service.IPartGroupService;
import eon.hg.fap.db.service.IRoleService;
import eon.hg.fap.db.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aeon
 *
 */
@Component
public class PartOP {
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IPartGroupService partService;
	
	public Dto PartGroupData2Dto(PartGroup pg) {
		Dto dto = new HashDto();
		dto.put("id", pg.getId());
		dto.put("code", pg.getId());
		dto.put("name", pg.getBelong_name());
		dto.put("text", pg.getBelong_name());
		dto.put("level", 1);
		dto.put("leaf", true);
		return dto;
	}
	
	public List<Dto> PartGroupList2DtoList(List<PartGroup> pgs) {
		List<Dto> list= new ArrayList<Dto>();
		for (PartGroup pg : pgs) {
			list.add(PartGroupData2Dto(pg));
		}
		return list;
	}

}
