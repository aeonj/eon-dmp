package eon.hg.fap.db.service.impl;

import cn.hutool.core.convert.Convert;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.cache.AbstractCacheOperator;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.dao.primary.GenericDao;
import eon.hg.fap.db.service.IBaseUIService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BaseUIServiceImpl extends AbstractCacheOperator implements IBaseUIService {

    @Resource
    GenericDao genericDao;

    @Override
    public String getCacheId(Object... params) {
        return "ui_view";
    }

    @Override
    public String getKey(Object... params) throws Exception {
        if (params != null && params.length > 0) {
            Map parameters = (Map) params[0];
            String sResult = "#ui=";
            if (CommUtil.isNotEmpty(parameters.get("servletpath"))
                    && CommUtil.isNotEmpty(parameters.get("comp_id"))) {
                sResult += parameters.get("servletpath") + "|"
                        + parameters.get("comp_id");
                return sResult;
            } else if (CommUtil.isNotEmpty(parameters.get("ui_id"))){
                sResult += Convert.toStr(parameters.get("ui_id"));
                return sResult;
            }
        }
        throw new Exception("界面视图参数传入错误");
    }

    @Override
    public String getObject(Object... params) throws Exception {
        if (params != null && params.length > 0) {
            try {
                Map dto = (Map) params[0];
                if (CommUtil.isEmpty(dto.get("ui_id"))) {
                    List<Dto> lstUI = genericDao.findDtoBySql("select id as ui_id from "+ Globals.SYS_TABLE_SUFFIX+"uimanager where servletpath=:servletpath and comp_id=:comp_id",dto);
                    if (lstUI==null || lstUI.size()==0) {
                        throw new Exception("界面视图不存在，请检查！");
                    }
                    dto.putAll(lstUI.get(0));
                }
                String sql ="select c.* from "+Globals.SYS_TABLE_SUFFIX+"uiconf_main c where c.ui_main_id=?";
                List<Dto> lstMain = genericDao.findDtoBySql(sql, new Object[]{dto.get("ui_id")});
                sql ="select b.*,u.total_column from "+Globals.SYS_TABLE_SUFFIX+"uidetail b,"+Globals.SYS_TABLE_SUFFIX+"uimanager u where u.id=b.ui_id and u.id=? order by b.field_index";
                List<Dto> lstDetail = genericDao.findDtoBySql(sql, new Object[]{dto.get("ui_id")});
                Dto dtoResult = new HashDto();
                dtoResult.put("main", lstMain);
                dtoResult.put("detail", getChildUIList(lstDetail));
                return JsonHandler.toJson(dtoResult);
            } catch (Exception e) {
                throw e;
            }
        }
        throw new Exception("传入的参数为空");
    }

    /**
     * 下拉树形集合
     * @param list
     * @return
     */
    public List getChildUIList(List list){
        for(int i=0; list!=null && i<list.size(); i++){
            Map rn = (Map) list.get(i);
            List<Dto> lstItems = genericDao.findDtoBySql("select c.* from "+Globals.SYS_TABLE_SUFFIX+"uiconf_detail c where c.ui_detail_id=?", new Object[]{rn.get("id")});
            rn.put("children", lstItems);
        }
        return list;
    }

}
