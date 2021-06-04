package eon.hg.fap.web.manage.op;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.dao.primary.GenericDao;
import eon.hg.fap.db.model.mapper.BaseData;
import eon.hg.fap.db.model.primary.Element;
import eon.hg.fap.db.model.primary.OrgType;
import eon.hg.fap.db.model.primary.Region;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.IBaseDataService;
import eon.hg.fap.db.service.IOrgTypeService;
import eon.hg.fap.db.service.IRegionService;
import eon.hg.fap.db.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
		List<User> userlist = this.userService.query("select obj from User obj where obj.manageType=0 and obj.rg_code=:rg_code and obj.is_deleted=0 order by obj.orgtype_id,obj.userName", new HashDto(){{put("rg_code",SecurityUserHolder.getRgCode());}}, -1, -1);
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

	public List<Dto> getUserTreeList_new() {
	    QueryObject qo = new QueryObject();
	    qo.setOrderBy("orgCode");
	    List<OrgType> orgTypes = this.orgTypeService.find(qo);
	    Map<Long,OrgType> mapOrgType = new HashMap<Long,OrgType>();
        Map<Long,Map<String,BaseData>> mapOrgBaseData = new HashMap<Long,Map<String,BaseData>>();
        Map<Long,Map<String,Dto>> mapOrgBaseDto = new HashMap<Long,Map<String,Dto>>();
	    for (OrgType orgType: orgTypes) {
	        mapOrgType.put(orgType.getId(),orgType);
            if (orgType!=null) {
                String ele_code = orgType.getEleCode();
                if (CommUtil.isNotEmpty(ele_code)) {
                    Element element = eleOp.getEleSource(ele_code);
                    try {
                        if (CommUtil.isNotEmpty(element.getClass_name())) {
                            qo = new QueryObject();
                            List<BaseData> bds = this.baseDataService.find(Class.forName(element.getClass_name()),qo);
                            Map<String,BaseData> mapBD = new HashMap<String,BaseData>();
                            for (BaseData bd: bds) {
                                mapBD.put(Convert.toStr(bd.getId()),bd);
                            }
                            mapOrgBaseData.put(orgType.getId(),mapBD);
                        } else {
                            List<Dto> dtoList = genericDao.findDtoBySql("select * from "+element.getEle_source()+" where is_deleted=0");
                            Map<String,Dto> mapDto = new HashMap<String,Dto>();
                            for (Dto dto :dtoList) {
                                mapDto.put(dto.getString("id"),dto);
                            }
                            mapOrgBaseDto.put(orgType.getId(),mapDto);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

		List<User> userlist = this.userService.query("select obj from User obj where obj.manageType=0 and obj.rg_code=:rg_code and obj.is_deleted=0 order by obj.orgtype_id,obj.userName", new HashDto(){{put("rg_code",SecurityUserHolder.getRgCode());}}, -1, -1);
		List<Dto> rootlist = new ArrayList<>();
		for (User user : userlist) {
		    OrgType orgType = mapOrgType.get(user.getOrgtype_id());
			if (orgType!=null) {
                if (mapOrgBaseData.get(orgType.getId())!=null) {
                    Map<String,BaseData> mapBD =mapOrgBaseData.get(orgType.getId());
                    BaseData bd = mapBD.get(user.getOrgtype_ele_id());
                    if (bd != null) {
                        bd.getChild().add(UserData2Dto(user));
                        addEleTree_new(orgType.getChild(), bd, mapBD);
                    } else {
                        orgType.getChild().add(UserData2Dto(user));
                    }
                } else if (mapOrgBaseDto.get(orgType.getId())!=null) {
                    //第三方物理表
                    Map<String,Dto> mapDto = mapOrgBaseDto.get(orgType.getId());
                    Dto bd = mapDto.get(user.getOrgtype_ele_id());
                    if (bd!=null) {
                        if (bd.containsKey("children")) {
                            List<Dto> bdchilds = (List<Dto>) bd.get("children");
                            bdchilds.add(UserData2Dto(user));
                        } else {
                            List<Dto> bdchilds =new ArrayList<>();
                            bdchilds.add(UserData2Dto(user));
                            bd.put("children",bdchilds);
                        }
                        addEleTree_new(mapDto,orgType.getChild(),bd);
                    } else {
                        orgType.getChild().add(UserData2Dto(user));
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
		dto.put("code", user.getUserName());
		dto.put("name", user.getTrueName());
		dto.put("text", user.getUserName()+" "+user.getTrueName());
		dto.put("leaf", true);
		dto.put("children", new ArrayList<Dto>());
		dto.put("type", "3");
		dto.put("glyph", "xf007@FontAwesome");
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
		dto.put("glyph", "xf19c@FontAwesome");
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
		dto.put("glyph", "xf233@FontAwesome");
		return dto;
	}

	private void addOrgTree(List<Dto> lst, OrgType node) {
		if (!ContainsNode(lst,node)) {
			lst.add(OrgData2Dto(node));
		}
	}

	private Dto getEleParent(String sql, List<Dto> lst, String id) {
		Stack<Dto> stack = new Stack();
		for (Dto dto: lst) {
			stack.push(dto);
		}
		//前序遍历
		while (!stack.isEmpty()) {
			Dto curr = stack.pop();
			//比较
			if (StrUtil.equals(curr.getString("id"),id)) {
				return curr;
			}
			if (curr.containsKey("children")) {
				List<Dto> childs = (List<Dto>) curr.get("children");
				if (childs != null && childs.size()>0) {
					for (Dto child : childs) {
						stack.push(child);
					}
				}
			}
		}
		//没有找到的处理
		List<Dto> dtoList = genericDao.findDtoBySql(sql,new Object[]{id});
		if (dtoList!=null && dtoList.size()>0) {
			Dto bd = dtoList.get(0);
			bd.put("children", new ArrayList<>());
			return bd;
		} else {
			return null;
		}
	}
	
	private Dto getEleParent_new(Map<String,Dto> mapDto, List<Dto> lst, String id) {
		Stack<Dto> stack = new Stack();
		for (Dto dto: lst) {
			stack.push(dto);
		}
		//前序遍历
		while (!stack.isEmpty()) {
			Dto curr = stack.pop();
			//比较
			if (StrUtil.equals(curr.getString("id"),id)) {
				return curr;
			}
			if (curr.containsKey("children")) {
				List<Dto> childs = (List<Dto>) curr.get("children");
				if (childs != null && childs.size()>0) {
					for (Dto child : childs) {
						stack.push(child);
					}
				}
			}
		}
		//没有找到的处理
		Dto bd = mapDto.get(id);
		if (bd!=null) {
			bd.put("children", new ArrayList<>());
			return bd;
		} else {
			return null;
		}
	}

	private void addEleTree(String sql, List<Dto> lst, Dto node) {
		node.put("text", node.getString("code")+" "+node.getString("name"));
		node.put("leaf", false);
		node.put("type", "2");
		Dto dtoParent=getEleParent(sql,lst,node.getString("parent_id"));
		if (dtoParent!=null) {
			List<Dto> pChilds = (List<Dto>) dtoParent.get("children");
			if (!ContainsNode(pChilds,node)) {
				pChilds.add(node);
			}
			addEleTree(sql, lst, dtoParent);
		} else {
			if (!ContainsNode(lst,node)) {
				lst.add(node);
			}
		}
	}
	
	private void addEleTree_new(Map<String,Dto> mapDto, List<Dto> lst, Dto node) {
		node.put("text", node.getString("code")+" "+node.getString("name"));
		node.put("leaf", false);
		node.put("type", "2");
		Dto dtoParent=getEleParent_new(mapDto,lst,node.getString("parent_id"));
		if (dtoParent!=null) {
			List<Dto> pChilds = (List<Dto>) dtoParent.get("children");
			if (!ContainsNode(pChilds,node)) {
				pChilds.add(node);
			}
			addEleTree_new(mapDto, lst, dtoParent);
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

	private void addEleTree_new(List<Dto> lst, BaseData node, Map<String,BaseData> mapBD) {
		BaseData parent = mapBD.get(node.getParent_id());
		if (parent!=null) {
			if (!ContainsNode(parent.getChild(),node)) {
				parent.getChild().add(BaseData2Dto(node));
			}
			addEleTree_new(lst,parent, mapBD);
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
