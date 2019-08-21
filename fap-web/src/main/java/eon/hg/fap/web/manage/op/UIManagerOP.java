package eon.hg.fap.web.manage.op;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.db.model.primary.UIConfDetail;
import eon.hg.fap.db.model.primary.UIDetail;
import eon.hg.fap.db.service.IUIManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 界面视图操作类，用于对界面视图的查询等操作
 * @author AEON
 *
 */
@Component
public class UIManagerOP {
    @Autowired
    private IUIManagerService uiManagerService;

    /**
     * 下拉树形集合
     * @param dto
     * @return
     */
    public List getUIDetailTreeChildNodeList(Dto dto, int level_num, boolean hasChecked){
        QueryObject qo = new QueryObject("field_index","asc");
        if (CommUtil.isNotEmpty(dto.get("parent_id")) && (!"0".equals(dto.get("parent_id")))) {
            qo.addQuery("obj.parent_id", "parent_id", CommUtil.null2Long(dto.get("parent_id")),"=");
        } else {
            qo.addQuery("obj.parent_id is null", null);
        }
        if (CommUtil.isNotEmpty(dto.get("ui_id"))) {
            qo.addQuery("obj.ui.id", "ui_id", CommUtil.null2Long(dto.get("ui_id")),"=");
        }
        if (CommUtil.isNotEmpty(dto.get("condition"))) {
            qo.addQuery(dto.getString("condition"),null);
        }
        List<UIDetail> uidetails = uiManagerService.findDetail(qo);
        List list = new ArrayList();
        for(UIDetail uidetail : uidetails){
            Dto rn = new HashDto();
            uidetail.setUi_detail_id(CommUtil.null2String(uidetail.getId()));
            WebHandler.toMap(uidetail, rn);
            rn.put("text", uidetail.getField_name()+" "+uidetail.getField_title());
            dto.put("parent_id", rn.getString("id"));
            rn.put("level_num", level_num);
            if (CommUtil.null2String(uidetail.getField_type()).equals("hidden")) {
                rn.put("iconCls", "nodeNoIcon");
            } else if (CommUtil.null2String(uidetail.getField_type()).equals("colmodel")) {
                try {
                    boolean is_hidden = false;
                    List<UIConfDetail> uiConfDetails = uidetail.getConfs();
                    if (uiConfDetails!=null && uiConfDetails.size()>0) {
                        for (UIConfDetail uiConfDetail : uiConfDetails) {
                            if ("hidden".equals(uiConfDetail.getUiconf_field())) {
                                is_hidden = "true".equals(uiConfDetail.getUiconf_value());
                            }
                        }
                    }
                    if (is_hidden) {
                        rn.put("iconCls", "nodeNoIcon");
                    } else {
                        rn.put("iconCls", "nodeYesIcon");
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    rn.put("iconCls", "nodeYesIcon");
                }
            } else {
                rn.put("iconCls", "nodeYesIcon");
            }
            List<Dto> innerList =getUIDetailTreeChildNodeList(dto,level_num+1,hasChecked);
            rn.put("children", innerList);
            if(hasChecked){
                rn.put("checked", false);
            }
            if(rn.getString("parent_id").equals('0')){
                rn.put("leaf", false);//第一级全部不是叶子
            }else{
                rn.put("leaf", innerList.size()==0?true:false);
            }
            rn.put("ui_detail_id",rn.getString("id"));
            list.add(rn);
        }
        return list;
    }

}
