package eon.hg.fap.db.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.cache.AbstractCacheOperator;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.dao.primary.ElementDao;
import eon.hg.fap.db.dao.primary.GenericDao;
import eon.hg.fap.db.dao.primary.PartGroupDao;
import eon.hg.fap.db.model.primary.Element;
import eon.hg.fap.db.model.primary.PartGroup;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.IBaseTreeService;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.third.IRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BaseTreeServiceImpl extends AbstractCacheOperator implements IBaseTreeService {

    @Autowired
    private ISysConfigService configService;
    @Autowired(required = false)
    private IRelation relation;

    @Resource
    ElementDao elementDAO;

    @Resource
    PartGroupDao partGroupDao;

    @Resource
    GenericDao genericDao;

    @Override
    public String getKey(Object... params) throws Exception {
        if (params != null && params.length > 0) {
            Dto parameters = (Dto) params[0];
            String sResult ="#eleset="+ SecureUtil.md5(parameters.toString());
//            if (CommUtil.isNotEmpty(parameters.get("source"))) {
//                sResult += parameters.getString("source");
//                sResult += "|"+ SecurityUserHolder.getOnlineUser().getUserid();
//                if (CommUtil.isNotEmpty(parameters.get("isfulldata"))) {
//                    sResult += "|"+parameters.getString("isfulldata");
//                }
//                if (CommUtil.isNotEmpty(parameters.get("ispermission"))) {
//                    sResult += "|"+parameters.getString("ispermission");
//                }
//                if (CommUtil.isNotEmpty(parameters.get("isfulllevel"))) {
//                    sResult += "|"+parameters.getString("isfulllevel");
//                }
//                if (CommUtil.isNotEmpty(parameters.get("selectmodel")) && parameters.getString("selectmodel").equals("multiple")) {
//                    sResult += "|"+parameters.getString("selectmodel");
//                }
//            }
            return sResult;
        }
        throw new Exception(getCacheId());
    }

    @Override
    public List<Dto> getObject(Object... params) throws Exception {
        if (!AeonConstants.VLicense.verify()) {
            throw new Exception("未经许可的认证，证书验证失败！");
        }
        if (params != null && params.length > 0) {
            try {
                Dto dto = (Dto) params[0];
                dto.remove("inner_condition");
                dto.remove("permission");
                dto.remove("relations");
                if (CommUtil.isNotEmpty(dto.getString("source"))) {
                    Element ele = this.elementDAO.getOne("ele_code",dto.getString("source"));
                    dto.put("table_name",ele.getEle_source());
                    if (CommUtil.isNotEmpty(dto.get("ispermission")) && dto.getBoolean("ispermission")) {
                        dto.put("permission", getPermissionSql(dto));
                    }
                    dto.put("relations", getRelationSql(dto));
                    if (configService.getSysConfig().isLogin_year()) {
                        dto.put("set_year",SecurityUserHolder.getSetYear());
                    }
                    if (ele.isFrom_rg()) {
                        dto.put("rg_code",SecurityUserHolder.getRgCode());
                    }
                    boolean isChecked = StrUtil.equalsIgnoreCase(dto.getString("selectmodel"), "multiple") ;
                    List<Dto> list = getChildNodeList(dto,1,isChecked);
                    return list;
                } else {
                    throw new Exception("没有找到对应的数据源。"+getCacheId()+":"+dto);
                }
            } catch (Exception e) {
                throw e;
            }
        }
        throw new Exception("传入的参数为空！");
    }

    /**
     * 下拉树形集合
     * @return
     */
    public List<Dto> getChildNodeList(Dto dto,int level_num,boolean hasChecked){
        List<Dto> list =findEleTree(dto);
        for(Dto rn:list){
            dto.put("parent_id", rn.getString("id"));
            if (CommUtil.isNotEmpty(dto.get("isfulllevel")) && !dto.getBoolean("isfulllevel")) {
                dto.put("parent_id", rn.getString("id"));
                List<Dto> countList =findEleTree(dto,true);
                if(hasChecked){
                    rn.put("checked", false);
                }
                if (countList!=null && countList.size()>0) {
                    rn.put("leaf", countList.get(0).getBigInteger("countnum").intValue()<=0?true:false);
                } else {
                    rn.put("leaf", false);
                }
            } else {
                List<Dto> innerList =null;
                if ("1".equals(rn.getString("leaf"))) {
                    rn.put("level_num", level_num);
                    //rn.put("children", new ArrayList<Dto>());
                } else {
                    innerList =getChildNodeList(dto,level_num+1,hasChecked);
                    rn.put("level_num", level_num);
                    rn.put("children", innerList);
                }
                if(hasChecked){
                    rn.put("checked", false);
                }
                rn.put("leaf", innerList==null || innerList.size()==0?true:false);
            }
        }
        return list;
    }

    public String getPermissionSql(Dto dto) {
        String sql = "";
        User cur_user = SecurityUserHolder.getCurrentUser();
        if (cur_user==null) {
            sql +=" and 1=0 ";
            return sql;
        }
        if (CommUtil.isNotEmpty(cur_user.getBelong_source())) {
            List<Dto> lstBelong = JsonHandler.parseList(cur_user.getBelong_source());
            if (dto.get("belong_source").equals(dto.getString("source"))) {
                for (Dto dtoBelong : lstBelong) {
                    if (dto.get("source").equals(dtoBelong.get("eleCode"))) {
                        if (System.getProperty("aeonDao.db").equals("oracle")) {
                            sql += "connect by prior e.parent_id=e.id\n" +
                                    " start with exists(\n" +
                                    "select 1 " +Globals.SYS_TABLE_SUFFIX + "userbelong o where o.user_id=" + cur_user.getId() + " and o.eleCode='" + dto.getString("source") + "' and o.value=e.id)";
                        } else {
                            sql += " and exists(select 1 from " +Globals.SYS_TABLE_SUFFIX + "userbelong o where o.user_id=" + cur_user.getId() + " and o.eleCode='" + dto.getString("source") + "' and o.value=e.id)";
                        }
                        break;
                    }
                }
            }
        } else if (CommUtil.isNotEmpty(cur_user.getPg_id()) && cur_user.getPg_id()!=-1l) {
            PartGroup pg = partGroupDao.get(cur_user.getPg_id());
            if (pg!=null) {
                List<Dto> lstBelong = JsonHandler.parseList(pg.getBelong_source());
                for (Dto dtoBelong : lstBelong) {
                    if (dto.get("source").equals(dtoBelong.get("eleCode"))) {
                        if (System.getProperty("aeonDao.db").equals("oracle")) {
                            sql += "connect by prior e.parent_id=e.id\n" +
                                    " start with exists(\n" +
                                    "select 1 " +Globals.SYS_TABLE_SUFFIX + "part_detail o where o.pg_id=" + pg.getId() + " and o.eleCode='" + dto.getString("source") + "' and o.value=e.id)";
                        } else {
                            sql += " and exists(select 1 from " +Globals.SYS_TABLE_SUFFIX + "part_detail o where o.pg_id=" + pg.getId() + " and o.eleCode='" + dto.getString("source") + "' and o.value=e.id)";
                        }
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
        StringBuilder sql = new StringBuilder();
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

                    String tmpsql = "select * from " +Globals.SYS_TABLE_SUFFIX + "relation_main where pri_ele='"+priSource+"' and sec_ele='"+secSource+"'";
                    List<Dto> lst = genericDao.findDtoBySql(tmpsql);
                    if (lst != null && lst.size() > 0) {
                        existsSqlCondition = true;
                        Dto dtoRm = lst.get(0);
                        if (System.getProperty("aeonDao.db").equals("oracle")) {
                            Element ele = this.elementDAO.getOne("ele_code",secSource);
                            String table_name = ele.getEle_source();
                            sql.append(" and e.id in (select et.id from ").append(table_name).append(" et connect by prior et.parent_id=et.id start with et.id in (select r.sec_ele_value from ").append(Globals.SYS_TABLE_SUFFIX).append("relation_detail r where r.main_id=").append(Convert.toStr(dtoRm.get("id")))
                                    .append(" and pri_ele_value='").append(priValue).append("'))");
                        } else {
                            sql.append(" and e.id in (select r.sec_ele_value from ").append(Globals.SYS_TABLE_SUFFIX).append("relation_detail r where r.main_id=").append(Convert.toStr(dtoRm.get("id")))
                                    .append(" and pri_ele_value='").append(priValue).append("')");
                        }
                    } else {
                        //找不到关联关系，找外部关联对象
                        if (relation!=null) {
                            String sqlThird =relation.getRelationSql(priSource,secSource,priValue);
                            if (StrUtil.isNotBlank(sqlThird)) {
                                existsSqlCondition = true;
                                sql.append(sqlThird);
                            }
                        }
                    }

                }
            }
            if (!existsSqlCondition) {
                sql.append(" and 1=0");
            }
        }
        return sql.toString();
    }

    private List findEleTree(Dto dtoParam) {
        return findEleTree(dtoParam, false);
    }

    private List findEleTree(Dto dtoParam, boolean iscount) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        if (iscount) {
            sql.append("count(1) as countnum");
        } else {
            if (System.getProperty("aeonDao.db").equals("oracle")) {
                sql.append("e.code||' '||e.name text, ");
            } else if (System.getProperty("aeonDao.db").equals("sqlserver")) {
                sql.append("e.code+' '+e.name text, ");
            } else if (System.getProperty("aeonDao.db").equals("mysql")) {
                sql.append("concat(e.code,' ',e.name) text, ");
            }
            if ("true".equals(dtoParam.getString("isfulldata"))) {
                sql.append("e.* ");
            } else {
                sql.append("e.id, e.code, e.name, e.leaf ");
            }
        }
        sql.append(" from ").append(dtoParam.getString("table_name")).append(" e ");
        sql.append(" WHERE e.enabled=1 ");
        if (CommUtil.isNotEmpty(dtoParam.get("parent_id"))) {
            if (dtoParam.getString("parent_id").equals("0")) {
                if (System.getProperty("aeonDao.db").equals("oracle")) {
                    sql.append(" and e.parent_id is null");
                } else if (System.getProperty("aeonDao.db").equals("sqlserver")) {
                    sql.append(" and isnull(e.parent_id,'0')='0'");
                } else if (System.getProperty("aeonDao.db").equals("mysql")) {
                    sql.append(" and ifnull(e.parent_id,'0')='0'");
                }
            } else {
                sql.append(" and e.parent_id='").append(dtoParam.getString("parent_id")).append("'");
            }
        }
        if (CommUtil.isNotEmpty(dtoParam.get("set_year"))) {
            sql.append(" and e.set_year=").append(dtoParam.getString("set_year"));
        }
        if (CommUtil.isNotEmpty(dtoParam.get("rg_code"))) {
            sql.append(" and e.rg_code='").append(dtoParam.getString("rg_code")).append("'");
        }
        if (CommUtil.isNotEmpty(dtoParam.get("condition"))) {
            sql.append(" and ").append(dtoParam.getString("condition"));
        }
        if (CommUtil.isNotEmpty(dtoParam.get("inner_condition"))) {
            sql.append(" and ").append(dtoParam.getString("inner_condition"));
        }
        if (CommUtil.isNotEmpty(dtoParam.get("permission"))) {
            sql.append(dtoParam.getString("permission"));
        }
        if (CommUtil.isNotEmpty(dtoParam.get("relations"))) {
            sql.append(dtoParam.getString("relations"));
        }
        sql.append(" order by e.code");
        List<Dto> bds = this.genericDao.findDtoBySql(sql.toString());
        return bds;
    }


    /**
     * 获取 cache id.
     *
     * @return the cacheId
     */
    @Override
    public String getCacheId(Object... params) {
        return "ele_table_values";
    }
}
