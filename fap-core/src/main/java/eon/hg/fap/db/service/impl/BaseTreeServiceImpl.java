package eon.hg.fap.db.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.cache.AbstractCacheOperator;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.dao.primary.ElementDao;
import eon.hg.fap.db.dao.primary.GenericDao;
import eon.hg.fap.db.model.primary.Element;
import eon.hg.fap.db.service.IBaseTreeService;
import eon.hg.fap.db.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BaseTreeServiceImpl extends AbstractCacheOperator implements IBaseTreeService {

    @Autowired
    private ISysConfigService configService;

    @Resource
    ElementDao elementDAO;

    @Resource
    GenericDao genericDao;

    @Override
    public String getKey(Object... params) throws Exception {
        if (params != null && params.length > 0) {
            Dto parameters = (Dto) params[0];
            String sResult ="#eleset=";
            if (CommUtil.isNotEmpty(parameters.get("source"))) {
                sResult += parameters.getString("source");
                sResult += "|"+ SecurityUserHolder.getCurrentUser().getId();
                if (CommUtil.isNotEmpty(parameters.get("isfulldata"))) {
                    sResult += "|"+parameters.getString("isfulldata");
                }
                if (CommUtil.isNotEmpty(parameters.get("ispermission"))) {
                    sResult += "|"+parameters.getString("ispermission");
                }
                if (CommUtil.isNotEmpty(parameters.get("isfulllevel"))) {
                    sResult += "|"+parameters.getString("isfulllevel");
                }
                if (CommUtil.isNotEmpty(parameters.get("selectmodel")) && parameters.getString("selectmodel").equals("multiple")) {
                    sResult += "|"+parameters.getString("selectmodel");
                }
            }
            return sResult;
        }
        throw new Exception(getCacheId());
    }

    @Override
    public List<Dto> getObject(Object... params) throws Exception {
        if (params != null && params.length > 0) {
            try {
                Dto dto = (Dto) params[0];
                dto.remove("inner_condition");
                dto.remove("permission");
                dto.remove("relations");
                if (CommUtil.isNotEmpty(dto.getString("source"))) {
                    Element ele = this.elementDAO.getBy("ele_code",dto.getString("source"));
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
                    rn.put("children", new ArrayList<Dto>());
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
        if (CommUtil.isNotEmpty(dto.get("loginuserid")) && CommUtil.isNotEmpty(dto.get("belong_source"))) {
            if (dto.get("belong_source").equals(dto.getString("source"))) {
                if (System.getProperty("aeonDao.db").equals("oracle")) {
                    sql += "connect by prior e.parent_id=e.chr_id\n" +
                            " start with exists(\n" +
                            "select o.org_id from ea_usermanage u,ea_user_org o\n" +
                            "  where o.org_id=e.chr_id and u.user_id=o.user_id and o.user_id='"+dto.getString("loginuserid")+"'\n" +
                            "  and u.org_type=(select ot.orgtype_code from ea_orgtype ot where ele_code='"+dto.getString("source")+"'))";
                } else {
                    sql += " and exists(\n" +
                            "select o.org_id from ea_usermanage u,ea_user_org o\n" +
                            "  where o.org_id=e.chr_id and u.user_id=o.user_id and o.user_id='"+dto.getString("loginuserid")+"'\n" +
                            "  and u.org_type=(select ot.orgtype_code from ea_orgtype ot where ele_code='"+dto.getString("source")+"'))";
                }
            }
        } else {
            sql +=" and 1=0 ";
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

                    String tmpsql = "select * from ea_relation_manager where pri_ele_code='"+priSource+"' and sec_ele_code='"+secSource+"'";
                    List<Dto> lst = genericDao.findDtoBySql(tmpsql);
                    if (lst != null && lst.size() > 0) {
                        existsSqlCondition = true;
                        Dto dtoRm = lst.get(0);
                        if (System.getProperty("aeonDao.db").equals("oracle")) {
                            Element ele = this.elementDAO.getBy("ele_code",dto.getString("source"));
                            String table_name = ele.getEle_source();
                            sql += "and e.chr_id in (select et.chr_id from " + table_name + " et connect by prior et.parent_id=et.chr_id\n" +
                                    " start with et.chr_id in (select r.sec_ele_value from ea_relations r where r.relation_id='" + Convert.toStr(dtoRm.get("relation_id")) +
                                    "' and pri_ele_value='" + priValue + "'))";
                        } else {
                            sql += " and e.chr_id in (select r.sec_ele_value from ea_relations r where r.relation_id='" + Convert.toStr(dtoRm.get("relation_id")) +
                                    "' and pri_ele_value='" + priValue + "')";
                        }
                    }

                }
            }
            if (!existsSqlCondition) {
                sql = " and 1=0";
            }
        }
        return sql;
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
