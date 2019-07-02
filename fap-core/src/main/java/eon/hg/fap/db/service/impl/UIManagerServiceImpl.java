package eon.hg.fap.db.service.impl;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.cache.AbstractCacheOperator;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.*;
import eon.hg.fap.db.model.primary.*;
import eon.hg.fap.db.service.IUIManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UIManagerServiceImpl extends AbstractCacheOperator implements IUIManagerService {
    @Resource
    UIManageDao uiManageDAO;

    @Resource
    UIDetailDao uiDetailDAO;

    @Resource
    UIConfMainDao uiConfMainDAO;

    @Resource
    UIConfDetailDao uiConfDetailDAO;

    @Resource
    UIConfDao uiConfDAO;

    @Resource
    GenericDao genericDao;

    private String cacheId = "uimanager";
    private Map<String,Map<String,Object>> typePool = new HashMap<>();

    /**
     * 保存一个UIManager，如果保存成功返回true，否则返回false
     *
     * @param instance
     * @return 是否保存成功
     */
    @Override
    public UIManager save(UIManager instance) {
        UIManager uiManager =this.uiManageDAO.save(instance);
//        for (UIDetail uiDetail : uiManager.getDetails()) {
//            this.uiDetailDAO.save(uiDetail);
//            for (UIConfDetail uiConfDetail : uiDetail.getConfs()) {
//                this.uiConfDetailDAO.save(uiConfDetail);
//            }
//        }
//        for (UIConfMain uiConfMain : uiManager.getConfs()) {
//            this.uiConfMainDAO.save(uiConfMain);
//        }
        return uiManager;
    }

    /**
     * 根据一个ID得到UIManager
     *
     * @param id
     * @return
     */
    @Override
    public UIManager getObjById(Long id) {
        return this.uiManageDAO.get(id);
    }

    /**
     * 删除一个UIManager
     *
     * @param id
     * @return
     */
    @Override
    public void delete(Long id) {
        this.uiManageDAO.remove(id);
    }

    /**
     * 通过一个查询对象得到UIManager
     *
     * @param properties
     * @return
     */
    @Override
    public IPageList list(IQueryObject properties) {
        return this.uiManageDAO.list(properties);
    }

    /**
     * 更新一个UIManager
     *
     * @param instance 需要更新的UIManager
     */
    @Override
    public UIManager update(UIManager instance) {
        UIManager uiManager = this.uiManageDAO.update(instance);
//        for (UIDetail uiDetail : uiManager.getDetails()) {
//            if (uiDetail.getId()==null) {
//                this.uiDetailDAO.save(uiDetail);
//            } else {
//                if (uiDetail.getIs_deleted()==1) {
//                    this.uiDetailDAO.delete(uiDetail);
//                }
//                this.uiDetailDAO.update(uiDetail);
//            }
//            for (UIConfDetail uiConfDetail : uiDetail.getConfs()) {
//                this.uiConfDetailDAO.save(uiConfDetail);
//            }
//        }
//        for (UIConfMain uiConfMain : uiManager.getConfs()) {
//            this.uiConfMainDAO.save(uiConfMain);
//        }
        return uiManager;
    }

    /**
     * @param query
     * @param params
     * @param begin
     * @param max
     * @return
     */
    @Override
    public List<UIManager> query(String query, Map params, int begin, int max) {
        return this.uiManageDAO.query(query,params,begin,max);
    }

    /**
     * @param properties
     * @return
     */
    @Override
    public List<UIManager> find(IQueryObject properties) {
        return this.uiManageDAO.find(properties);
    }

    /**
     * @param construct
     * @param propertyName
     * @param value
     * @return
     */
    @Override
    public UIManager getObjByProperty(String construct, String propertyName, Object value) {
        return this.uiManageDAO.getBy(construct,propertyName,value);
    }

    /**
     * @param properties
     * @return
     */
    @Override
    public List<UIDetail> findDetail(IQueryObject properties) {
        return this.uiDetailDAO.find(properties);
    }

    @Override
    public IPageList listConf(QueryObject qoconf) {
        return this.uiConfDAO.list(qoconf);
    }

    @Override
    public List<UIConfDetail> findConfDetail(QueryObject qoconfdetail) {
        return this.uiConfDetailDAO.find(qoconfdetail);
    }

    @Override
    public List<UIConfMain> findConfMain(QueryObject qoconfmain) {
        return this.uiConfMainDAO.find(qoconfmain);
    }

    @Override
    public List<UIConf> findConf(QueryObject qoconf) {
        return this.uiConfDAO.find(qoconf);
    }

    @Override
    public String getCacheId(Object... params) {
        if (params != null && params.length > 1) {
            return params[0]+"_"+params[1];
        } else {
            return this.cacheId;
        }
    }

    @Override
    public String getKey(Object... params) throws Exception {
        if (params != null && params.length > 2) {
            String key = (String) params[2];
            if (CommUtil.isNotEmpty(key)) {
                return "#id="+key;
            } else {
                return "#id=-1";
            }
        }
        throw new Exception("参数传入错误");
    }

    @Override
    public List<Dto> getObject(Object... params) throws Exception {
        if (params != null && params.length > 2) {
            if ("detail".equals(params[0])) {
                try {
                    String ui_id = (String) params[2];
                    String sql_other = "";
                    if (System.getProperty("aeonDao.db").equals("oracle")) {
                        sql_other = ",t.id||'' as ui_detail_id";
                    } else if (System.getProperty("aeonDao.db").equals("sqlserver")) {
                        sql_other = ",t.id+'' as ui_detail_id";
                    } else if (System.getProperty("aeonDao.db").equals("mysql")) {
                        sql_other = ",concat(t.id,'') as ui_detail_id";
                    }
                    return this.genericDao.findDtoBySql("select t.*" + sql_other + " from " + Globals.SYS_TABLE_SUFFIX + "uidetail t where t.ui_id=?", new Object[]{ui_id});
                } catch (Exception e) {
                    throw e;
                }
            } else if ("confdetail".equals(params[0])) {
                try {
                    String uiconf_id = (String) params[2];
                    String sql_other="";
                    if (System.getProperty("aeonDao.db").equals("oracle")) {
                        sql_other =",t.ui_detail_id||'' uiconf_id";
                    } else if (System.getProperty("aeonDao.db").equals("sqlserver")) {
                        sql_other =",t.ui_detail_id+'' uiconf_id";
                    } else if (System.getProperty("aeonDao.db").equals("mysql")) {
                        sql_other =",concat(t.ui_detail_id,'') uiconf_id";
                    }
                    return this.genericDao.findDtoBySql("select t.*"+sql_other+" from "+Globals.SYS_TABLE_SUFFIX+"uiconf_detail t where t.ui_detail_id=?", new Object[]{uiconf_id});
                } catch (Exception e) {
                    throw e;
                }
            } else if ("confmain".equals(params[0])) {
                try {
                    String uiconf_id = (String) params[2];
                    String sql_other="";
                    if (System.getProperty("aeonDao.db").equals("oracle")) {
                        sql_other =",t.ui_main_id||'' uiconf_id";
                    } else if (System.getProperty("aeonDao.db").equals("sqlserver")) {
                        sql_other =",t.ui_main_id+'' uiconf_id";
                    } else if (System.getProperty("aeonDao.db").equals("mysql")) {
                        sql_other =",concat(t.ui_main_id,'') uiconf_id";
                    }
                    return this.genericDao.findDtoBySql("select t.*"+sql_other+" from "+Globals.SYS_TABLE_SUFFIX+"uiconf_main t where t.ui_main_id=?", new Object[]{uiconf_id});
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return null;
    }

}
