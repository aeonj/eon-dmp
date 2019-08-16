package eon.hg.fap.web.manage.op;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.dao.primary.GenericDao;
import eon.hg.fap.db.model.primary.Element;
import eon.hg.fap.db.model.primary.OrgType;
import eon.hg.fap.db.model.primary.Region;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.model.mapper.BaseData;
import eon.hg.fap.db.service.IBaseDataService;
import eon.hg.fap.db.service.IOrgTypeService;
import eon.hg.fap.db.service.IRegionService;
import eon.hg.fap.db.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户工具类
 * @author aeon
 *
 */
@Component
public class UserOP {
	@Autowired
	private IUserService userService;
	@Autowired
	private ElementOP eleOp;
	@Autowired
	private IBaseDataService baseDataService;
	@Autowired
	private IRegionService regionService;
	@Autowired
	private IOrgTypeService orgTypeService;
	@Autowired
	private GenericDao genericDao;

	public List<Dto> getUserTreeList() {
		List<User> userlist = this.userService.query("select obj from User obj where obj.manageType=0 and obj.rg_code=:rg_code and obj.is_deleted=0 order by obj.orgtype_id", new HashDto(){{put("rg_code",SecurityUserHolder.getRgCode());}}, -1, -1);
		List<Dto> rootlist = new ArrayList<>();
		for (User user : userlist) {
		    OrgType orgType = this.orgTypeService.getObjById(user.getOrgtype_id());
			if (orgType!=null) {
				String ele_code = orgType.getEleCode();
				if (CommUtil.isNotEmpty(ele_code)) {
					Element element = eleOp.getEleSource(ele_code);
					try {
						if (CommUtil.isNotEmpty(element.getClass_name())) {
							BaseData bd = this.baseDataService.getObjById(Class.forName(element.getClass_name()), Convert.toLong(user.getOrgtype_ele_id(),Long.valueOf(-1)));
							if (bd != null) {
								bd.getChild().add(UserData2Dto(user));
								addEleTree(orgType.getChild(), bd);
							} else {
								orgType.getChild().add(UserData2Dto(user));
							}
						} else {
							//第三方物理表
							String sql = "select * from "+element.getEle_source()+" where id=?";
							List<Dto> dtoList = genericDao.findDtoBySql(sql,new Object[]{user.getOrgtype_ele_id()});
							if (dtoList!=null && dtoList.size()>0) {
								Dto bd = dtoList.get(0);
								if (bd.containsKey("children")) {
									List<Dto> bdchilds = (List<Dto>) bd.get("children");
									bdchilds.add(UserData2Dto(user));
								} else {
									List<Dto> bdchilds =new ArrayList<>();
									bdchilds.add(UserData2Dto(user));
									bd.put("children",bdchilds);
								}
								addEleTree(sql,orgType.getChild(),bd);
							} else {
								orgType.getChild().add(UserData2Dto(user));
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				} else {
                    orgType.getChild().add(UserData2Dto(user));
				}
				addOrgTree(rootlist,orgType);
			}
		}
		return rootlist;
	}

	private Dto UserData2Dto(User user) {
		Dto dto = new HashDto();
		dto.put("id", user.getId());
		dto.put("code", user.getUsername());
		dto.put("name", user.getTrueName());
		dto.put("text", user.getUsername()+" "+user.getTrueName());
		dto.put("leaf", true);
		dto.put("children", new ArrayList<Dto>());
		dto.put("type", "3");
		dto.put("iconSkin", "user");
		return dto;
	}

	private Dto BaseData2Dto(BaseData bd) {
		Dto dto = new HashDto();
		dto.put("id", bd.getId());
		dto.put("code", bd.getCode());
		dto.put("name", bd.getName());
		dto.put("text", bd.getCode()+" "+bd.getName());
		dto.put("leaf", false);
		dto.put("children", bd.getChild());
		dto.put("type", "2");
		return dto;
	}

	private Dto OrgData2Dto(OrgType org) {
		Dto dto = new HashDto();
		dto.put("id", org.getId());
		dto.put("code", org.getOrgCode());
		dto.put("name", org.getOrgName());
		dto.put("text", org.getOrgCode()+" "+org.getOrgName());
		dto.put("leaf", false);
		dto.put("children", org.getChild());
		dto.put("type", "1");
		return dto;
	}

	private void addOrgTree(List<Dto> lst, OrgType node) {
		if (!ContainsNode(lst,node)) {
			lst.add(OrgData2Dto(node));
		}
	}

	private Dto getEleParent(String sql,String id) {
		List<Dto> dtoList = genericDao.findDtoBySql(sql,new Object[]{id});
		if (dtoList!=null && dtoList.size()>0) {
			Dto bd = dtoList.get(0);
			return bd;
		} else {
			return null;
		}
	}
	
	private void addEleTree(String sql, List<Dto> lst, Dto node) {
		node.put("text", node.getString("code")+" "+node.getString("name"));
		node.put("leaf", false);
		node.put("type", "2");
		Dto dtoParent=getEleParent(sql,node.getString("parent_id"));
		if (dtoParent!=null) {
			dtoParent.put("children", new ArrayList<>());
			List<Dto> pChilds = (List<Dto>) dtoParent.get("children");
			if (!ContainsNode(pChilds,node)) {
				pChilds.add(node);
			}
			addEleTree(sql, lst,dtoParent);
		} else {
			if (!ContainsNode(lst,node)) {
				lst.add(node);
			}
		}
	}
	
	private void addEleTree(List<Dto> lst, BaseData node) {
		BaseData parent = node.getParent();
		if (parent!=null) {
			if (!ContainsNode(parent.getChild(),node)) {
				parent.getChild().add(BaseData2Dto(node));
			}
			addEleTree(lst,parent);
		} else {
			if (!ContainsNode(lst,node)) {
				lst.add(BaseData2Dto(node));
			}
		}
	}

	private boolean ContainsNode(List<Dto> lst, Dto node) {
		for (Dto dto : lst) {
			if (StrUtil.equals(dto.getString("id"),node.getString("id"))) {
				return true;
			}
		}
		return false;
	}
	
	private boolean ContainsNode(List<Dto> lst, IdEntity node) {
		for (Dto dto : lst) {
			if (dto.getLong("id").longValue()==node.getId().longValue()) {
				return true;
			}
		}
		return false;
	}

	public Region getCurrRegion() {
		return this.regionService.getObjByProperty(null, "code", SecurityUserHolder.getRgCode());
	}
	
	public String getRgEname() {
		Region region = getCurrRegion();
		if (region!=null)
			return region.getEname();
		return "";
	}
}
