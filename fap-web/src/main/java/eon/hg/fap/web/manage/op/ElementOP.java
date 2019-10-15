/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月4日
 * @author cxj
 */

package eon.hg.fap.web.manage.op;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.common.util.tools.StringUtils;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.db.dao.primary.GenericDao;
import eon.hg.fap.db.model.mapper.BaseData;
import eon.hg.fap.db.model.primary.*;
import eon.hg.fap.db.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础数据操作类，用于对基础数据的查询等操作
 * @author AEON
 *
 */
@Component
@Slf4j
public class ElementOP {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IElementService elementService;
	@Autowired
	private IBaseDataService baseDataService;
	@Autowired
	private GenericDao genericDao;
	@Autowired
	private RegionOP regionOp;
	@Autowired
    private IPartGroupService partGroupService;
	@Autowired
    private IRelationMainService relationMainService;

	/**
	 * 根据要素编码，获取要素信息
	 * @param source
	 * @return
	 */
	public Element getEleSource(String source) {
		return this.elementService.getObjByProperty(null, "ele_code", source);
	}

	/**
	 * 根据要素和Id，返回BaseData形式的基础数据，不支持主键ID为字符串
	 * @param source
	 * @param value
	 * @return
	 */
	public BaseData getBaseDataById(String source, Long value) {
		Element element = getEleSource(source);
		try {
			if (element!=null) {
				String class_name = element.getClass_name();
				if (CommUtil.isNotEmpty(class_name)) {
					return this.baseDataService.getObjById(Class.forName(class_name), CommUtil.null2Long(value));
				} else {
					StringBuffer sql = new StringBuffer();
					sql.append("SELECT e.* ");
					sql.append(" from ").append(element.getEle_source()).append(" e ");
					sql.append(" where e.id=?");
					List<BaseData> list = this.baseDataService.findBySql(sql.toString(),new Object[]{value});
					if (list!=null && list.size()>0) {
						return list.get(0);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据要素和Id，返回Map形式的基础数据，支持主键ID为字符串
	 * @param source
	 * @param value
	 * @return
	 */
	public Dto getBaseDtoById(String source, String value) {
		Element element = getEleSource(source);
		if (element!=null) {
			String class_name = element.getClass_name();
			if (CommUtil.isEmpty(class_name)) {
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT e.* ");
				sql.append(" from ").append(element.getEle_source()).append(" e ");
				sql.append(" where e.id=?");
				List<Dto> list = this.genericDao.findDtoBySql(sql.toString(),new Object[]{value});
				if (list!=null && list.size()>0) {
					return list.get(0);
				}
			} else {
				BaseData bd = getBaseDataById(source, Convert.toLong(value,-1l));
				Dto dto = new HashDto();
				BeanUtil.beanToMap(bd,dto,true,(key) -> (key.toLowerCase()));
				return dto;
			}
		}
		return null;
	}

	/**
	 * 根据要素和编码，返回BaseData形式的基础数据，不支持主键ID为字符串
	 * @param source
	 * @param value
	 * @return
	 */
	public BaseData getBaseDataByCode(String source, String value) {
		Element element = getEleSource(source);
		try {
			if (element!=null) {
				String class_name = element.getClass_name();
				if (CommUtil.isNotEmpty(class_name)) {
					return this.baseDataService.getObjByProperty(Class.forName(class_name), "code", value);
				} else {
			    	StringBuffer sql = new StringBuffer();
					sql.append("SELECT e.* ");
					sql.append(" from ").append(element.getEle_source()).append(" e ");
					sql.append(" where e.code=?");
					List<BaseData> list = this.baseDataService.findBySql(sql.toString(),new Object[]{value});
					if (list!=null && list.size()>0) {
						return list.get(0);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据要素和编码，返回Map形式的基础数据，支持主键ID为字符串
	 * @param source
	 * @param value
	 * @return
	 */
	public Dto getBaseDtoByCode(String source, String value) {
		Element element = getEleSource(source);
		if (element!=null) {
			String class_name = element.getClass_name();
			if (CommUtil.isEmpty(class_name)) {
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT e.* ");
				sql.append(" from ").append(element.getEle_source()).append(" e ");
				sql.append(" where e.code=?");
				List<Dto> list = this.genericDao.findDtoBySql(sql.toString(),new Object[]{value});
				if (list!=null && list.size()>0) {
					return list.get(0);
				}
			}
		} else {
			BaseData bd = getBaseDataByCode(source,value);
			Dto dto = new HashDto();
			BeanUtil.beanToMap(bd,dto,true,(key) -> (key.toLowerCase()));
			return dto;
		}
		return null;
	}

	public List<Dto> getObject(Dto dto) throws Exception {
		if (!AeonConstants.VLicense.verify()) {
			throw new Exception("未经许可的认证，证书验证失败！");
		}
		if (CommUtil.isNotEmpty(dto.getString("source"))) {
			Element element = getEleSource(dto.getString("source"));
			dto.put("table_name",element.getEle_source());
			dto.put("class_name",element.getClass_name());
			if (CommUtil.isNotEmpty(dto.get("ispermission")) && dto.getBoolean("ispermission")) {
				dto.put("permission", getPermissionSql(dto));
			}
            dto.put("relations", getRelationSql(dto));
			boolean isChecked = dto.getString("selectmodel").equalsIgnoreCase("multiple") ;
			boolean onlyname = CommUtil.isNotEmpty(dto.get("onlyname")) && dto.getBoolean("onlyname");
			boolean isfulldata = CommUtil.isNotEmpty(dto.get("isfulldata")) && dto.getBoolean("isfulldata");
			//List<Dto> list = getChildNodeList(dto,1,isChecked);
			List<Dto> list = getTreeNodeList(dto,isChecked,onlyname,isfulldata);
			return list;
		} else {
			return new ArrayList();
		}
	}
	
	private List<Dto> getTreeNodeList(Dto dtoParam, boolean hasChecked, boolean onlyname, boolean isfulldata) {
		List<Dto> lstTree = new ArrayList<Dto>();
		for (BaseData bd : queryTreeList(dtoParam)) {
			addNodeChild(lstTree,bd,onlyname,isfulldata);
		}
		if (hasChecked) {
			CommUtil.setCheckTreeList(lstTree,dtoParam.getString("checkids"));
		}
		return lstTree;
	}
	
	private List<BaseData> queryTreeList(Dto dtoParam) {
		StringBuffer sql = new StringBuffer();
		sql.append("select obj from ").append(dtoParam.getString("class_name")).append(" obj");
		sql.append(" where obj.is_deleted=0 and obj.enabled=1");
	    if (CommUtil.isNotEmpty(dtoParam.get("condition"))) {
	    	sql.append(" and ").append(dtoParam.getString("condition"));
	    }
	    if (CommUtil.isNotEmpty(dtoParam.get("permission"))) {
	    	sql.append(dtoParam.getString("permission"));
	    }
	    if (CommUtil.isNotEmpty(dtoParam.get("relations"))) {
	    	sql.append(dtoParam.getString("relations"));
	    }
	    if (CommUtil.isExistsAttr(dtoParam.getString("class_name"), "rg_code")) {
	    	if (dtoParam.getString("source").equals("PBC_ORG")
	    			|| dtoParam.getString("source").equals("FINANCE_ORG")) {
	    		//人行机构和金融机构特殊处理，自动带上子集
	    		if (!SecurityUserHolder.getRgCode().equals(AeonConstants.SUPER_RG_CODE)) {
	    			//超级区划不过滤
		    		String in_rgs = this.regionOp.getPermissionRegions(SecurityUserHolder.getRgCode());
		    		if (CommUtil.isNotEmpty(dtoParam.get("containparent"))) {
		    			if (dtoParam.getBoolean("containparent")) {
				    		//支持上级区划 
				    		Area pararea = this.regionOp.getCurrentArea().getParent();
				    		while (pararea!=null) {
					    		if (in_rgs.isEmpty()) {
					    			in_rgs = CommUtil.null2String(pararea.getId());
					    		} else {
					    			in_rgs = CommUtil.null2String(pararea.getId())+","+in_rgs;
					    		}
					    		pararea = pararea.getParent();
				    		}
				    		if (in_rgs.isEmpty()) {
				    			in_rgs = AeonConstants.SUPER_RG_CODE;
				    		} else {
				    			in_rgs = AeonConstants.SUPER_RG_CODE+","+in_rgs;
				    		}
				    		//end 支持上级区划
		    			}
		    		}
		    		sql.append(" and obj.rg_code in (").append(convertInSql(in_rgs)).append(")");
	    		}
	    	} else if (dtoParam.getString("source").equals("PBC_EXT")
	    			|| dtoParam.getString("source").equals("FINANCE_EXT")) {
	    		//人行机构和金融机构特殊处理，获取外部区划权限
	    		if (!SecurityUserHolder.getRgCode().equals(AeonConstants.SUPER_RG_CODE)) {
	    			//超级区划不过滤
		    		String in_rgs = this.regionOp.getExtenalRegions(SecurityUserHolder.getRgCode());
		    		sql.append(" and obj.rg_code in (").append(convertInSql(in_rgs)).append(")");
	    		}
	    	} else if (dtoParam.getString("source").equals("FINANCE")) {
	    		if (CommUtil.null2String(this.configService.getSysConfig().getWelcomeType()).equals("2")) {
	    			//非金融的金融机构默认全部权限
	    			//sql.append(" and obj.rg_code!='").append(AeonConstants.SUPER_RG_CODE).append("'");
	    		} else {
	    			sql.append(" and obj.rg_code='").append(SecurityUserHolder.getRgCode()).append("'");
	    		}
	    	} else {
	    		sql.append(" and obj.rg_code='").append(SecurityUserHolder.getRgCode()).append("'");
	    	}
	    }
	    //sql.append(" order by obj.level,obj.code");
		return this.baseDataService.query(sql.toString(), null, -1, -1);
	}
	
	private String convertInSql(String codes) {
		String result = "'"+ StringUtils.replace(codes, ",", "','")+"'";
		return result;
	}
	
	private void addNodeChild(List<Dto> lst, BaseData node, boolean onlyname,boolean isfulldata) {

		if (node.getParent_id()!=null) {
			BaseData parent = this.baseDataService.getObjById(node.getClass(),node.getParent_id());
			if (parent==null) {
				log.error("后台数据源错误，未找到父节点",node);
			}
			int idx = ContainsNode(parent.getChild(),node);
			if (idx!=-1) {
				parent.getChild().add(idx,BaseData2Dto(node,onlyname,isfulldata));
			}
			addNodeChild(lst,parent,onlyname,isfulldata);
		} else {
			int idx = ContainsNode(lst,node);
			if (idx!=-1) {
				lst.add(idx,BaseData2Dto(node,onlyname,isfulldata));
			}
		}
	}
	
	private int ContainsNode(List<Dto> lst, BaseData node) {
		boolean is_standard = false;
		if (this.userConfigService.getUserConfig()!=null) {
			String codetype = this.userConfigService.getUserConfig().getElecode_type();
			if (codetype!=null && codetype.equals("standard")) {
				is_standard = true;
			}
		}
		for (int i=0; i<lst.size(); i++) {
			Dto dto = lst.get(i);
			if (dto.getInteger("id").intValue()==node.getId().intValue()) {
				return -1;
			} else {
				String code = node.getCode();
				if (is_standard) {
					code = node.getStandard_code();
				}
				int retval = dto.getString("code").compareTo(CommUtil.null2String(code));
				if (retval>=0)
					return i;
			}
		}
		return lst.size();
	}

	private Dto BaseData2Dto(BaseData bd, boolean onlyname,boolean isfulldata) {
		String code = bd.getCode();
		Dto dto = new HashDto();
		dto.put("id", bd.getId());
		if (this.userConfigService.getUserConfig()!=null) {
			String codetype = this.userConfigService.getUserConfig().getElecode_type();
			if (codetype!=null && codetype.equals("standard")) {
				code = bd.getStandard_code();
			}
		}
		dto.put("code", code);
		dto.put("name", bd.getName());
		if (onlyname) {
			dto.put("text", bd.getName());
		} else {
			dto.put("text", code+" "+bd.getName());
		}
        dto.put("children", bd.getChild());
		if (isfulldata) {
			WebHandler.toMap(bd, dto);
		}
		dto.put("leaf", bd.isLeaf());
		dto.put("level", bd.getLevel());
		dto.put("treepath", bd.getTreepath());
		return dto;
	}

	private List<Dto> getChildNodeList(Dto dto, int level_num, boolean hasChecked) {
        List<Dto> list =findEleTree(dto);
        for(Dto rn:list){
        	dto.put("parent_id", rn.getString("id"));
        	List<Dto> innerList =null;
			if (CommUtil.isNotEmpty(dto.get("isfulllevel")) && !dto.getBoolean("isfulllevel")) {
	        	dto.put("parent_id", rn.getString("id"));
	            innerList =findEleTree(dto);
			} else {
	            innerList =getChildNodeList(dto,level_num+1,hasChecked);
	            rn.put("level_num", level_num);
	            rn.put("children", innerList);
			}
            if(hasChecked){
            	rn.put("checked", false);
            }
            if(rn.getString("parent_id").equals('0')){
            	rn.put("leaf", false);//第一级全部不是叶子
            }else{
            	rn.put("leaf", innerList==null || innerList.size()==0?true:false);
            }
        }
        return list;

	}

	private List<Dto> findEleTree(Dto dtoParam) {
		StringBuffer sql = new StringBuffer();
		sql.append("select obj from ").append(dtoParam.getString("class_name")).append(" obj");
		sql.append(" where obj.is_deleted=0 and obj.enabled=1");
		if (CommUtil.isNotEmpty(dtoParam.get("parent_id"))) {
			sql.append(" and obj.parent.id=").append(dtoParam.getString("parent_id"));
	    }
	    if (CommUtil.isNotEmpty(dtoParam.get("condition"))) {
	    	sql.append(" and ").append(dtoParam.getString("condition"));
	    }
	    if (CommUtil.isNotEmpty(dtoParam.get("permission"))) {
	    	sql.append(" and ").append(dtoParam.getString("permission"));
	    }
	    if (CommUtil.isExistsAttr(dtoParam.getString("class_name"), "rg_code")) {
	    	sql.append(" and obj.rg_code='").append(SecurityUserHolder.getRgCode()).append("'");
	    }
	    sql.append(" order by obj.code");
		List<BaseData> bds = this.baseDataService.query(sql.toString(), null, -1, -1);
		List<Dto> lst = new ArrayList<Dto>();
		for (BaseData bd :bds) {
			Dto dto = new HashDto();
			dto.put("id", bd.getId());
			dto.put("code", bd.getCode());
			dto.put("name", bd.getName());
			dto.put("text", bd.getCode()+" "+bd.getName());
			if (dto.getString("isfulldata").equals("true")) {
				WebHandler.toMap(bd, dto);
			}
			lst.add(dto);
		}
		return lst;
	}

	private String getPermissionSql(Dto dto) {
		String sql = "";
        //判断本要素是否需要筛选
        User cur_user = SecurityUserHolder.getCurrentUser();
        if (CommUtil.isNotEmpty(cur_user.getBelong_source())) {
            List<Dto> lstBelong = JsonHandler.parseList(cur_user.getBelong_source());
            if (dto.get("belong_source").equals(dto.getString("source"))) {
                for (Dto dtoBelong : lstBelong) {
                    if (dto.get("source").equals(dtoBelong.get("eleCode"))) {
                        sql += " and exists(select 1 from UserBelong o where o.user_id=" + cur_user.getId() + " and o.eleCode='" + dto.getString("source") + "' and o.value=obj.id)";
                        break;
                    }
                }
            }
        } else if (CommUtil.isNotEmpty(cur_user.getPg_id()) && cur_user.getPg_id()!=-1l) {
            PartGroup pg = partGroupService.getObjById(cur_user.getPg_id());
            if (pg!=null) {
                List<Dto> lstBelong = JsonHandler.parseList(pg.getBelong_source());
                for (Dto dtoBelong : lstBelong) {
                    if (dto.get("source").equals(dtoBelong.get("eleCode"))) {
                        sql += " and exists(select 1 from PartDetail o where o.pg.id=" + pg.getId() + " and o.eleCode='" + dto.getString("source") + "' and o.value=obj.id)";
                        break;
                    }
                }
            }
    	} else {
    		//sql += " and 1=0 ";
    	}
    	return sql;
	}

    public String getRelationSql(Dto dto) {
        String sql = "";
        //要素关联关系
        if (CommUtil.isNotEmpty(dto.get("relationfilter"))) {
            boolean existsSqlCondition = false;
            //解析json
            List lstRela = JsonHandler.parseList(dto.getString("relationfilter"));
            if (lstRela!=null && lstRela.size()>0) {
                for (int i = 0; i < lstRela.size(); i++) {
                    Dto dtoRela = (Dto) lstRela.get(i);
                    String priSource = dtoRela.getString("source");
                    String priValue = dtoRela.getString("value");
                    String secSource = dto.getString("source");

                    RelationMain relationMain = relationMainService.getObjByProperty("pri_ele",priSource,"sec_ele",secSource);
                    if (relationMain!=null) {
                        sql += " and exists(select 1 from RelationDetail r where r.main.id=" + relationMain.getId() + " and r.pri_ele_value='" + priValue + "' and r.sec_ele_value=obj.id)";
                        existsSqlCondition = true;
                    }
                }
            }
            if (!existsSqlCondition) {
                sql = " and 1=0";
            }
        }
        return sql;
    }
	
	public List getEleCodeList() {
		return new ArrayList();
	}

	public String getBaseNameById(String source, Long value) {
		BaseData bd = getBaseDataById(source, value);
		if (bd==null) {
			return "";
		}
		return bd.getName();
	}

	/**
	 * 多选树设置选择节点
	 * @param list
	 * @param Ids
	 * @return
	 */
	public boolean setCheckNodeList(List list, String Ids) {
		boolean isChecked = false;
		for(int i=0; list!=null && i<list.size(); i++){
			Dto rn = (Dto) list.get(i);
			List innerList = rn.getList("children");
			rn.put("checked", false);
			//判断是否已选
			if (innerList==null || innerList.size()==0) {
				if (IsCheckedInIds(rn,Ids)) {
					isChecked = true;
					rn.put("checked", true);
				}
			} else {
				if (setCheckNodeList(rn.getList("children"),Ids)) {
					isChecked = true;
					rn.put("checked", true);
				}
				//innerList裡面存在checked為真的，澤這裡賦值為真
				//rn.put("checked", ExistsChildCheck(innerList));
			}
		}
		return isChecked;
	}

	private boolean IsCheckedInIds(Dto rn, String Ids) {
		if (CommUtil.isEmpty(Ids))
			return false;
		String[] arrayIds = Ids.split(",");
		for (int i=0; arrayIds!=null && i<arrayIds.length; i++) {
			if (arrayIds[i].equals(rn.getString("code"))) {
				return true;
			}
			if (arrayIds[i].equals(rn.getString("id"))) {
				return true;
			}
		}
		return false;
	}


}
