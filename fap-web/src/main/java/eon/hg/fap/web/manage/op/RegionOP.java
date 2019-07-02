package eon.hg.fap.web.manage.op;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.model.primary.*;
import eon.hg.fap.db.service.IRegionService;
import eon.hg.fap.db.service.IRoleService;
import eon.hg.fap.db.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author aeon
 *
 */
@Component
public class RegionOP extends TreeSort{
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IRegionService regionService;
	
	public User getUserProperty(String propertyName, Object value) {
		StringBuffer sb = new StringBuffer("select obj from User obj ");
		sb.append(" where obj.").append(propertyName).append(" = :value");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("value", value);
		List<User> ret = this.userService.query(sb.toString(), params, -1, -1);
		if (ret != null && ret.size() == 1) {
			return ret.get(0);
		} else if (ret != null && ret.size() > 1) {
			throw new IllegalStateException(
					"worning  --more than one object find!!");
		} else {
			return null;
		}
	}

	public User getUserProperty(Map<String, Object> params) {
		StringBuffer sb = new StringBuffer("select obj from User obj ");
		sb.append(" where 1=1 ");
		Iterator<String> its = params.keySet().iterator();
		while (its.hasNext()) {
			String key = its.next();
			sb.append(" and obj.").append(key).append(" = :").append(key);
		}
		List<User> ret = this.userService.query(sb.toString(), params, -1, -1);
		if (ret != null && ret.size() == 1) {
			return ret.get(0);
		} else if (ret != null && ret.size() > 1) {
			throw new IllegalStateException(
					"worning  --more than one object find!!");
		} else {
			return null;
		}
	}

	public Role getRoleProperty(String propertyName, Object value) {
		StringBuffer sb = new StringBuffer("select obj from Role obj ");
		sb.append(" where obj.").append(propertyName).append(" = :value");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("value", value);
		List<Role> ret = this.roleService.query(sb.toString(), params, -1, -1);
		if (ret != null && ret.size() == 1) {
			return ret.get(0);
		} else if (ret != null && ret.size() > 1) {
			throw new IllegalStateException(
					"worning  --more than one object find!!");
		} else {
			return null;
		}
	}

	public Role getRoleProperty(Map<String, Object> params) {
		StringBuffer sb = new StringBuffer("select obj from Role obj ");
		sb.append(" where 1=1 ");
		Iterator<String> its = params.keySet().iterator();
		while (its.hasNext()) {
			String key = its.next();
			sb.append(" and obj.").append(key).append(" = :").append(key);
		}
		List<Role> ret = this.roleService.query(sb.toString(), params, -1, -1);
		if (ret != null && ret.size() == 1) {
			return ret.get(0);
		} else if (ret != null && ret.size() > 1) {
			throw new IllegalStateException(
					"worning  --more than one object find!!");
		} else {
			return null;
		}
	}
	
	public Dto RegionData2Dto(Region bd) {
		Dto dto = new HashDto();
		dto.put("id", bd.getId());
		dto.put("code", bd.getCode());
		dto.put("name", bd.getName());
		dto.put("text", bd.getEname()+" "+bd.getName()+"管理员");
		dto.put("leaf", true);
		dto.put("iconSkin", "user");
		return dto;
	}
	
	public List<Dto> getRgLocalAreaTreeList() {
		QueryObject qo = new QueryObject();
		qo.setOrderBy("code");
		List<Region> rglist = this.regionService.find(qo);
		if (!SecurityUserHolder.getRgCode().equals(AeonConstants.SUPER_RG_CODE)) {
			rglist = getRegionPermissionTree(rglist);
			//加上本级
			rglist.add(getCurrentRegion());
		}
		List<Dto> rootlist = new ArrayList<Dto>();
		for (Region region : rglist) {
			if (region.getArea()!=null) {
				addAreaTree(rootlist,region.getArea());
			}
		}
		return rootlist;
	}
	public List<Dto> getRgAreaTreeList() {
		QueryObject qo = new QueryObject();
		qo.setOrderBy("code");
		List<Region> rglist = this.regionService.find(qo);
		if (!SecurityUserHolder.getRgCode().equals(AeonConstants.SUPER_RG_CODE)) {
			rglist = getRegionPermissionTree(rglist);
		}
		List<Dto> rootlist = new ArrayList<Dto>();
		for (Region region : rglist) {
			if (region.getArea()!=null) {
				addAreaTree(rootlist,region.getArea());
			}
		}
		return rootlist;
	}
	public List<Dto> getRegionTreeList() {
		QueryObject qo = new QueryObject();
		List<Region> rglist = this.regionService.find(qo);
		if (!SecurityUserHolder.getRgCode().equals(AeonConstants.SUPER_RG_CODE)) {
			rglist = getRegionPermissionTree(rglist);
		}
		List<Dto> rootlist = new ArrayList<Dto>();
		for (Region region : rglist) {
			if (region.getArea()!=null) {
				List<Dto> nodelist = region.getArea().getSons();
				nodelist.add(RegionData2Dto(region));
				addAreaTree(rootlist,region.getArea());
			}
		}
		return rootlist;
	}
	private void addAreaTree(List<Dto> lst, Area node) {
		if (node.getParent()!=null) {
			Area parent = node.getParent();
			List<Dto> parlist = parent.getSons();
			if (!ContainsNode(parlist,node)) {
				parlist.add(AreaData2Dto(node));
			}
			addAreaTree(lst,parent);
		} else {
			if (!ContainsNode(lst,node)) {
				lst.add(AreaData2Dto(node));
			}
		}
	}
	
	private boolean ContainsNode(List<Dto> lst, Area node) {
		for (Dto dto : lst) {
			if (dto.getLong("id").longValue()==node.getId().longValue()) {
				return true;
			}
		}
		return false;
	}

	public Dto AreaData2Dto(Area area) {
		Dto dto = new HashDto();
		dto.put("id", area.getId());
		dto.put("code", CommUtil.null2String(area.getId()));
		dto.put("name", area.getAreaName());
		dto.put("text", area.getId()+" "+area.getAreaName());
		dto.put("children", area.getSons());
		dto.put("open", true);
		dto.put("leaf", false);
		return dto;
	}

	public List<Dto> RegionList2DtoList(List<Region> rgs) {
		List<Dto> list= new ArrayList<Dto>();
		for (Region rg : rgs) {
			list.add(RegionData2Dto(rg));
		}
		return list;
	}
	
	public Region getCurrentRegion() {
		Region region = this.regionService.getObjByProperty(null, "code", SecurityUserHolder.getRgCode());
		return region;
	}

	public Area getCurrentArea() {
		return getCurrentRegion().getArea();
	}
	
	public List<Region> getRegionPermissionTree(List<Region> rglist) {
		Area curr_area = getCurrentArea();
		List<Region> lstResult = new ArrayList<Region>();
		for (Region region : rglist) {
			if (isRegionChild(curr_area,region.getArea())) {
				lstResult.add(region);
			}
		}
		return lstResult;
	}
	
	private boolean isRegionChild(Area parent, Area child) {
		if (child.getParent()==null) {
			return false;
		} else {
			if (parent.equals(child.getParent())) {
				return true;
			} else {
				return isRegionChild(parent,child.getParent());
			}
		}
	}

	public List<Dto> getMenuTreeList(Dto dtoParam) {
		Region region = this.regionService.getObjById(CommUtil.null2Long(dtoParam.get("region_id")));
		Collection<Menu> menus = region.getMenus();
		List<Dto> lstTree = new ArrayList<Dto>();
		for (Menu menu : menus) {
			if (menu.getMg()!=null) {
				menu.getMg().getChild().add(MenuData2Dto(menu));
				addMgTree(lstTree,menu.getMg());
			}
		}
		sortMenuList(lstTree);
		for (Dto dto : lstTree) {
			List<Dto> childs = (List<Dto>) dto.get("children");
			sortMenuList(childs);
		}
		boolean isChecked = dtoParam.getString("selectmodel").equalsIgnoreCase("multiple") ;
		if (isChecked) {
			String checkids = dtoParam.getString("checkids");
			List<String> lstchecks = null;
			if (CommUtil.isNotEmpty(checkids)) {
				String[] arr = checkids.split(",");
				lstchecks = Arrays.asList(arr);
			}
			setCheckTreeList(lstTree,lstchecks);
		}
		return lstTree;
	}

	private void addMgTree(List<Dto> lst, MenuGroup node) {
		if (!ContainsNode(lst,node)) {
			lst.add(MgData2Dto(node));
		}
	}
	
	private boolean ContainsNode(List<Dto> lst, IdEntity node) {
		for (Dto dto : lst) {
			if (dto.getLong("id").longValue()==node.getId().longValue()) {
				return true;
			}
		}
		return false;
	}
	
	private Dto MgData2Dto(MenuGroup org) {
		Dto dto = new HashDto();
		dto.put("id", org.getId());
		dto.put("code", org.getId());
		dto.put("name", org.getName());
		dto.put("text", org.getName());
		dto.put("leaf", false);
		dto.put("children", org.getChild());
		dto.put("type", "1");
		dto.put("sequence", org.getSequence());
		return dto;
	}

	private Dto MenuData2Dto(Menu node) {
		Dto dto = new HashDto();
		dto.put("id", node.getId());
		dto.put("code", node.getMenuCode());
		dto.put("name", node.getMenuName());
		dto.put("text", node.getMenuName());
		dto.put("leaf", true);
		dto.put("children", new ArrayList<Dto>());
		dto.put("type", "2");
		dto.put("sequence", node.getSequence());
		return dto;
	}

	public void setCheckTreeList(List<Dto> lst,List<String> checkids) {
		for (Dto dto : lst) {
			if (checkids!=null) {
				dto.put("checked", checkids.contains(dto.getString("id")));
			} else {
				dto.put("checked", false);
			}
			setCheckTreeList((List<Dto>) dto.getList("children"),checkids);
		}
	}
	
	/**
	 * 获取指定Rgcode的下级区划
	 *     可考虑后期固化到core包，参考user.rg_code的固化
	 * @param rgcode
	 * @return
	 */
	public String getPermissionRegions(String rgcode) {
		Region region = this.regionService.getObjByProperty(null, "code", rgcode);
		if (region!=null) {
			QueryObject qo = new QueryObject();
			List<Region> rglist = this.regionService.find(qo);
			StringBuilder sb = new StringBuilder();
			sb.append(region.getCode());
			for (Region rg : rglist) {
				if (isRegionChild(region.getArea(),rg.getArea())) {
					sb.append(",").append(rg.getCode());
				}
			}
			return sb.toString();
		} else {
			return "";
		}
	}
	
	/**
	 * 获取指定RgCode的区划关系授权
	 * @param rgcode
	 * @return
	 */
	public String getExtenalRegions(String rgcode) {
		Region region = this.regionService.getObjByProperty(null, "code", rgcode);
		if (region!=null) {
			StringBuilder sb = new StringBuilder();
			sb.append(region.getCode());
			for (Region rg : region.getRelations()) {
				sb.append(",").append(rg.getCode());
			}
			return sb.toString();
		} else {
			//这里为负1，表示没有相应的权限
			return "-1";
		}
	}
	
	/**
	 * 获取指定Rgcode的下级区划列表
	 * @param rgcode
	 * @return
	 */
	public List<Region> getPermissionRegionList(String rgcode) {
		Region region = this.regionService.getObjByProperty(null, "code", rgcode);
		if (region!=null) {
			QueryObject qo = new QueryObject();
			List<Region> rglist = this.regionService.find(qo);
			List<Region> lstResult = new ArrayList<Region>();
			for (Region rg : rglist) {
				if (isRegionChild(region.getArea(),rg.getArea())) {
					lstResult.add(rg);
				}
			}
			return lstResult;
		} else {
			return null;
		}
	}
}
