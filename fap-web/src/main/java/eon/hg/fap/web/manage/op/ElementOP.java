/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月4日
 * @author cxj
 */

package eon.hg.fap.web.manage.op;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.tools.tree.List2Tree;
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
import java.util.Map;

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
				try {
					BaseData bd = this.baseDataService.getObjById(Class.forName(class_name), CommUtil.null2Long(value));
					Dto dto = new HashDto();
					BeanUtil.beanToMap(bd,dto,true,(key) -> (key.toLowerCase()));
					return dto;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
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
			} else {
				try {
					BaseData bd = this.baseDataService.getObjByProperty(Class.forName(class_name), "code", value);
					Dto dto = new HashDto();
					BeanUtil.beanToMap(bd, dto, true, (key) -> (key.toLowerCase()));
					return dto;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
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
			//List<Dto> list = getChildNodeList(dto,1,isChecked);
			List<Dto> list = getTreeNodeList(dto);
			return list;
		} else {
			return new ArrayList();
		}
	}
	
	private List<Dto> getTreeNodeList(Dto dtoParam) {
		List<BaseData> bds = queryTreeList(dtoParam);
		ElementTreeDto treeDto = new ElementTreeDto();
		treeDto.setChecked(dtoParam.getString("selectmodel").equalsIgnoreCase("multiple"));
		treeDto.setOnlyName(CommUtil.isNotEmpty(dtoParam.get("onlyname")) && dtoParam.getBoolean("onlyname"));
		treeDto.setFullData(CommUtil.isNotEmpty(dtoParam.get("isfulldata")) && dtoParam.getBoolean("isfulldata"));
		treeDto.setStandard(false);
		UserConfig userConfig = this.userConfigService.getUserConfig();
		if (userConfig!=null) {
			String codetype = userConfig.getElecode_type();
			if (codetype!=null && codetype.equals("standard")) {
				treeDto.setStandard(true);
			}
		}
		String checkids = dtoParam.getString("checkids");
		if (CommUtil.isNotEmpty(checkids)) {
			String[] arr = checkids.split(",");
			treeDto.setCheckids(arr);
		}

		List2Tree<BaseData> tree = new List2Tree<BaseData>() {
			@Override
			protected Object getId(BaseData node) {
				return node.getId();
			}

			@Override
			protected Object getPid(BaseData node) {
				return node.getParent_id();
			}

			@Override
			protected void setText(BaseData node) {

			}

			@Override
			protected List<BaseData> getChildren(BaseData node) {
				return node.getChildren();
			}

			@Override
			protected void setChildren(List<BaseData> nodes, BaseData node) {
				node.setChildren(nodes);
			}

			/**
			 * 本方法为支持结果集只有叶子节点的情况，需要从仓库载入父亲节点的情况
			 * @param map
			 * @param node
			 * @return
			 */
			@Override
			protected BaseData getTopParent(Map<Object,BaseData> map, BaseData node) {
				if (getPid(node)==null) {
					return node;
				} else {
					BaseData parent = baseDataService.getObjById(node.getClass(), node.getParent_id());
					if (parent!=null) {
						map.put(getId(parent), parent);
						if (getChildren(parent)==null) {
							List<BaseData> ch = new ArrayList<>();
							ch.add(node);
							setChildren(ch, parent);
						} else {
							List<BaseData> ch = getChildren(parent);
							ch.add(node);
						}
						return getTopParent(map, parent);
					} else {
						return node;
					}
				}
			}
		};
		bds = tree.buildTree(bds,"code");
		//转换为dto，并设置leaf和check属性
		List<Dto> lstTree = ListBase2Dto(bds,treeDto);
//		for (BaseData bd : bds) {
//			addNodeChild(lstTree,bd, treeDto);
//		}
//		if (treeDto.isChecked) {
//			CommUtil.setCheckTreeList(lstTree,dtoParam.getString("checkids"));
//		}
		return lstTree;
	}

	private List<Dto> ListBase2Dto(List<BaseData> bds, ElementTreeDto treeDto) {
		List<Dto> treeList = new ArrayList<>();
		if (CollectionUtil.isNotEmpty(bds)) {
			for (BaseData bd : bds) {
				Dto node = BaseData2Dto(bd,treeDto);
				node.put("children",ListBase2Dto(bd.getChildren(),treeDto));
				if (treeDto.isChecked) {
					node.put("checked", ArrayUtil.containsIgnoreCase(treeDto.getCheckids(),bd.getId().toString()));
				}
				if (CollectionUtil.isEmpty(bd.getChildren())) {
					node.remove("children");
					node.put("leaf",true);
				} else {
					node.put("leaf",false);
				}
				treeList.add(node);
			}
		}
		return treeList;
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
	    //sql.append(" order by obj.code,obj.level");
		return this.baseDataService.query(sql.toString(), null, -1, -1);
	}
	
	private String convertInSql(String codes) {
		String result = "'"+ StringUtils.replace(codes, ",", "','")+"'";
		return result;
	}
	
	private void addNodeChild(List<Dto> lst, BaseData node, ElementTreeDto treeDto) {
		if (node.getParent_id()!=null) {
			BaseData parent = node.getTree_parent();
			if (parent==null) {
				parent = this.baseDataService.getObjById(node.getClass(), node.getParent_id());
				node.setTree_parent(parent);
			}
			if (parent==null) {
				log.error("后台数据源错误，未找到父节点",node);
			}
			int idx = ContainsNode(parent.getChild(),node,treeDto.isStandard());
			if (idx!=-1) {
				parent.getChild().add(idx,BaseData2Dto(node,treeDto));
			}
			addNodeChild(lst,parent,treeDto);
		} else {
			int idx = ContainsNode(lst,node,treeDto.isStandard());
			if (idx!=-1) {
				lst.add(idx,BaseData2Dto(node,treeDto));
			}
		}
	}

	private int ContainsNode(List<Dto> lst, BaseData node,boolean is_standard) {
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

	private Dto BaseData2Dto(BaseData bd, ElementTreeDto treeDto) {
		Dto dto = new HashDto();
		dto.put("id", bd.getId());
		String code = treeDto.isStandard() ? bd.getStandard_code() : bd.getCode();
		dto.put("code", code);
		dto.put("name", bd.getName());
		if (treeDto.isOnlyName()) {
			dto.put("text", bd.getName());
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(bd.getCode()).append(" ").append(bd.getName());
			dto.put("text", sb.toString());
		}
        dto.put("children", bd.getChild());
		if (treeDto.isFullData()) {
			WebHandler.toMap(bd, dto);
		}
		dto.put("leaf", bd.isLeaf());
		dto.put("level", bd.getLevel());
		dto.put("treepath", bd.getTreepath());
		return dto;
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

	private class ElementTreeDto {
		private boolean isChecked;
		private boolean onlyName;
		private boolean isFullData;
		private boolean isStandard;
		private String[] checkids;

		public boolean isChecked() {
			return isChecked;
		}

		public void setChecked(boolean checked) {
			isChecked = checked;
		}

		public boolean isOnlyName() {
			return onlyName;
		}

		public void setOnlyName(boolean onlyName) {
			this.onlyName = onlyName;
		}

		public boolean isFullData() {
			return isFullData;
		}

		public void setFullData(boolean fullData) {
			isFullData = fullData;
		}

		public boolean isStandard() {
			return isStandard;
		}

		public void setStandard(boolean standard) {
			isStandard = standard;
		}

		public String[] getCheckids() {
			return checkids;
		}

		public void setCheckids(String[] checkids) {
			this.checkids = checkids;
		}
	}

}
