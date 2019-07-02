package eon.hg.fap.web.manage.action;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.domain.virtual.SysMap;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.db.model.primary.*;
import eon.hg.fap.db.service.*;
import eon.hg.fap.security.annotation.SecurityMapping;
import eon.hg.fap.web.manage.op.UIManagerOP;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/manage")
public class UIManageAction extends BizAction {
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IUIManagerService uiManagerService;
    @Autowired
    private IElementService elementService;
    @Autowired
    private UIManagerOP uiManagerOP;
    @Autowired
    HttpSession httpSession;
    @Autowired
    IBaseUIService baseUIService;

    @SecurityMapping(title = "界面视图管理",value = "ui:view")
    @RequestMapping("/ui_manage.htm")
    public ModelAndView ui_manage(HttpServletRequest request,
                                    HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("fap/ui_manage.html",
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        return mv;
    }

    @SecurityMapping("ui:view")
    @RequestMapping("/query_uimanager_view.htm")
    public @ResponseBody String query_uimanager_view(Long ui_id) throws Exception {
        UIManager uimanager = uiManagerService.getObjById(ui_id);
        if (uimanager!=null)
            uimanager.setUi_id(CommUtil.null2String(uimanager.getId()));
        return JsonHandler.toExtJson(uimanager);
    }

    @SecurityMapping("ui:view")
    @RequestMapping("/query_uimanager.htm")
    public @ResponseBody String query_uimanager(Long ui_id) throws Exception {
        UIManager uimanager = uiManagerService.getObjById(ui_id);
        uiManagerService.removeLocal(new Object[]{"detail",httpSession.getId()});
        uiManagerService.removeLocal(new Object[]{"confmain",httpSession.getId()});
        uiManagerService.removeLocal(new Object[]{"confdetail",httpSession.getId()});
        if (uimanager!=null)
            uimanager.setUi_id(CommUtil.null2String(uimanager.getId()));
        uiManagerService.getPoolCache(new Object[]{"detail", httpSession.getId(), Convert.toStr(ui_id)});
        uiManagerService.getPoolCache(new Object[]{"confdetail", httpSession.getId(), Convert.toStr(ui_id)});
        uiManagerService.getPoolCache(new Object[]{"confmain", httpSession.getId(), Convert.toStr(ui_id)});
        return JsonHandler.toExtJson(uimanager);
    }

    @SecurityMapping("ui:view")
    @RequestMapping("/ui_nullstore.htm")
    public @ResponseBody String ui_nullstore(String ui_id) {
        List lstView = new ArrayList();
        Integer pageCount = Integer.valueOf(0);
        String jsonString = JsonHandler.toPageJson(lstView, pageCount);
        return jsonString;
    }

    @SecurityMapping("ui:view")
    @RequestMapping("/ui_detail_tree.htm")
    public @ResponseBody String ui_detail_tree(@RequestParam Map<String, Object> map, @RequestParam("node") String parent_id) {
        Dto dto = new HashDto(map);
        dto.put("parent_id", parent_id);
        List list = uiManagerOP.getUIDetailTreeChildNodeList(dto,1,false);
        return JsonHandler.toJson(list);
    }

    @SecurityMapping("ui:view")
    @RequestMapping("/query_uiconfmain.htm")
    public @ResponseBody String query_uiconfmain(String uiconf_id, String xtype) {
        QueryObject qoconf = new QueryObject();
        qoconf.addQuery("obj.uiconf_type","xtype",xtype,"=");
        qoconf.setOrderBy("order_no");
        List<UIConf> uiConfList= this.uiManagerService.findConf(qoconf);
        QueryObject qoconfmain = new QueryObject();
        qoconfmain.addQuery("obj.ui_main.id","uiconf_id",CommUtil.null2Long(uiconf_id),"=");
        List<UIConfMain> uiconfmains = this.uiManagerService.findConfMain(qoconfmain);
        for (UIConf dtoUiConf : uiConfList) {
            dtoUiConf.setUiconf_value(null);
            dtoUiConf.setIs_contain(0);
            dtoUiConf.setEditmode("0");
            for (UIConfMain uiconfmain : uiconfmains) {
                if (StrUtil.equals(uiconfmain.getUiconf_field(), dtoUiConf.getUiconf_field())) {
                    dtoUiConf.setUiconf_value(uiconfmain.getUiconf_value());
                    dtoUiConf.setIs_contain(1);
                    break;
                }
            }
        }
        return JsonHandler.toPageJson(uiConfList);
    }

    @SecurityMapping("ui:view")
    @RequestMapping("/query_uiconfdetail.htm")
    public @ResponseBody String query_uiconfdetail(String uiconf_id, String xtype) {
        QueryObject qoconf = new QueryObject();
        qoconf.addQuery("obj.uiconf_type","xtype",xtype,"=");
        qoconf.setOrderBy("order_no");
        List<UIConf> uiConfList= this.uiManagerService.findConf(qoconf);
        QueryObject qoconfdetail = new QueryObject();
        qoconfdetail.addQuery("obj.ui_detail.id","uiconf_id",CommUtil.null2Long(uiconf_id),"=");
        List<UIConfDetail> uiconfdetails = this.uiManagerService.findConfDetail(qoconfdetail);
        for (UIConf dtoUiConf : uiConfList) {
            dtoUiConf.setUiconf_value(null);
            dtoUiConf.setIs_contain(0);
            dtoUiConf.setEditmode("0");
            for (UIConfDetail uiconfdetail : uiconfdetails) {
                if (StrUtil.equals(uiconfdetail.getUiconf_field(), dtoUiConf.getUiconf_field())) {
                    dtoUiConf.setUiconf_value(uiconfdetail.getUiconf_value());
                    dtoUiConf.setIs_contain(1);
                    break;
                }
            }
        }
        return JsonHandler.toPageJson(uiConfList);
    }

    @RequestMapping("/append_uicolcache.htm")
    public @ResponseBody Dto append_uicolcache(@RequestParam Map<String, Object> inDto) throws Exception {
        inDto.remove("reqCode");
        inDto.remove("loginuserid");
        inDto.put("ui_detail_id", Convert.toStr(new Date().getTime()));
        List<Map> dsDetail =uiManagerService.getPoolCache(new Object[]{"detail", httpSession.getId(), Convert.toStr(inDto.get("ui_id"))});
        Dto outDto = new HashDto();
        if (dsDetail!=null) {
            inDto.put("hidden", 0);
            inDto.put("field_index", dsDetail.size()+1);
            inDto.put("detail_type", 0);
            if (CommUtil.ContainsNode(dsDetail, (Map map) -> (map.get("field_name").equals(inDto.get("field_name"))))) {
                outDto.put("msg", "新增字段已存在");
                outDto.put("success", AeonConstants.FALSE);
            } else {
                dsDetail.add(inDto);
                inDto.put("id", inDto.get("ui_detail_id"));
                inDto.put("code", inDto.get("field_name"));
                inDto.put("name", inDto.get("field_title"));
                inDto.put("text", inDto.get("field_name")+" "+inDto.get("field_title"));
                if ("hidden".equals(inDto.get("field_type"))) {
                    inDto.put("iconCls", "nodeNoIcon");
                } else {
                    inDto.put("iconCls", "nodeYesIcon");
                }
                outDto.put("data", inDto);
                outDto.put("msg", "字段新增成功");
                outDto.put("success", AeonConstants.TRUE);
            }
        }
        return outDto;

    }

    /**
     * 界面视图删除缓存字段
     * @param inDto
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete_uicolcache.htm")
    public @ResponseBody Dto delete_uicolcache(@RequestParam Map<String, Object> inDto) throws Exception {
        List<Map> dsDetail =uiManagerService.getPoolCache(new Object[]{"detail", httpSession.getId(), Convert.toStr(inDto.get("ui_id"))});
        Dto outDto = new HashDto();
        if (dsDetail!=null) {
            for (Map dsMap : dsDetail) {
                if (Convert.toStr(dsMap.get("ui_detail_id")).equals(Convert.toStr(inDto.get("ui_detail_id")))) {
                    dsDetail.remove(dsMap);
                    List<Map> dsUiConfDetail =uiManagerService.getPoolCache(new Object[]{"confdetail", httpSession.getId(), inDto.get("ui_detail_id")});
                    dsUiConfDetail.clear();
                    outDto.put("msg", "字段删除成功");
                    outDto.put("success", AeonConstants.TRUE);
                    return outDto;
                }
            }
            outDto.put("msg", "指定字段不存在，删除失败");
            outDto.put("success", AeonConstants.FALSE);
        }
        return outDto;
    }

    /**
     * 界面视图主表缓存列
     * @param uiconf_id
     * @param xtype
     * @return
     * @throws Exception
     */
    @RequestMapping("/cache_uiconfmain.htm")
    public @ResponseBody String cache_uiconfmain(String uiconf_id, String xtype) throws Exception {
        QueryObject qo = new QueryObject();
        if (CommUtil.isNotEmpty(xtype)) {
            qo.addQuery("obj.uiconf_type",
                    new SysMap("xtype", xtype), "=");
        }
        qo.setOrderBy("order_no");
        List<UIConf> uiConfList = this.uiManagerService.findConf(qo);
        List<Map> dsUiConfMain =uiManagerService.getPoolCache(new Object[]{"confmain", httpSession.getId(), uiconf_id});
        //删除不存在的属性记录 add by cxj 2016.5.27
        Iterator<Map> iter = dsUiConfMain.iterator();
        while (iter.hasNext()) {
            Map mapConfMain = iter.next();
            boolean isfind=false;
            for (UIConf uiConf : uiConfList) {
                if (mapConfMain.get("uiconf_field").equals(uiConf.getUiconf_field())) {
                    isfind=true;
                    break;
                }
            }
            if (!isfind) {
                dsUiConfMain.remove(mapConfMain);
            }
        }
        //end added

        for (UIConf dtoUiConf : uiConfList) {
            if (CommUtil.ContainsNode(dsUiConfMain, (Map map) -> {
                boolean isexists = map.get("uiconf_field").equals(dtoUiConf.getUiconf_field());
                if (isexists) {
                    dtoUiConf.setUiconf_value(Convert.toStr(map.get("uiconf_value")));
                }
                return isexists;
            })) {
                dtoUiConf.setIs_contain(1);

            } else {
                dtoUiConf.setIs_contain(0);
            }
        }
        return JsonHandler.toPageJson(uiConfList);

    }

    /**
     * 界面视图缓存列
     * @param uiconf_id
     * @param xtype
     * @return
     * @throws Exception
     */
    @RequestMapping("/cache_uiconfdetail.htm")
    public @ResponseBody String cache_uiconfdetail(String uiconf_id, String xtype) throws Exception {
        QueryObject qo = new QueryObject();
        if (CommUtil.isNotEmpty(xtype)) {
            qo.addQuery("obj.uiconf_type",
                    new SysMap("xtype", xtype), "=");
        }
        qo.setOrderBy("order_no");
        List<UIConf> uiConfList = this.uiManagerService.findConf(qo);
        List<Map> dsUiConfDetail =uiManagerService.getPoolCache(new Object[]{"confdetail", httpSession.getId(), uiconf_id});
        //删除不存在的属性记录 add by cxj 2016.5.27
        Iterator<Map> iter = dsUiConfDetail.iterator();
        while (iter.hasNext()) {
            Map mapConfDetail = iter.next();
            boolean isfind=false;
            for (UIConf uiConf : uiConfList) {
                if (mapConfDetail.get("uiconf_field").equals(uiConf.getUiconf_field())) {
                    isfind=true;
                    break;
                }
            }
            if (!isfind) {
                dsUiConfDetail.remove(mapConfDetail);
            }
        }
        //end added

        for (UIConf dtoUiConf : uiConfList) {
            if (CommUtil.ContainsNode(dsUiConfDetail, (Map map) -> {
                boolean isexists = map.get("uiconf_field").equals(dtoUiConf.getUiconf_field());
                if (isexists) {
                    dtoUiConf.setUiconf_value(Convert.toStr(map.get("uiconf_value")));
                }
                return isexists;
            })) {
                dtoUiConf.setIs_contain(1);

            } else {
                dtoUiConf.setIs_contain(0);
            }
        }
        return JsonHandler.toPageJson(uiConfList);

    }

    /**
     * 界面视图调序缓存字段
     * @param inDto
     * @throws Exception
     */
    @RequestMapping("/swap_uicolcache.htm")
    public void swap_uicolcache(@RequestParam Map<String, Object> inDto) throws Exception {
        Dto swapDto = JsonHandler.parseDto((String)inDto.get("swap"));
        List<Map> dsDetail =uiManagerService.getPoolCache(new Object[]{"detail", httpSession.getId(), Convert.toStr(inDto.get("ui_id"))});
        if (dsDetail!=null) {
            //顺序存在bug，将原有交换顺序号的代码换成更新为所有顺序号（同一父节点）重排  modify by cxj 2016.5.27
            for (Map map:dsDetail) {
                if (swapDto.containsKey(map.get("ui_detail_id"))) {
                    map.put("field_index",swapDto.get(map.get("ui_detail_id")));
                }
            }
        }
    }

    /**
     * 更新缓冲表，存入缓冲区
     * @param inDto
     * @throws Exception
     */
    @RequestMapping("/update_uiconfandmain_cache.htm")
    public @ResponseBody Dto update_uiconfandmain_cache(@RequestParam Map<String, Object> inDto, String dirtydata) throws Exception {
        inDto.remove("dirtydata");
        List<Map> dsConfMain =uiManagerService.getPoolCache(new Object[]{"confmain", httpSession.getId(), Convert.toStr(inDto.get("ui_id"))});
        List list  = JsonHandler.parseList(dirtydata);
        for (int i=0; i<list.size(); i++) {
            Dto dtoClientConfMain = (Dto) list.get(i);
            if (!CommUtil.ContainsNode(dsConfMain, (Map map) -> {
                boolean isexists = map.get("uiconf_field").equals(dtoClientConfMain.get("uiconf_field"));
                if (isexists) {
                    if ("1".equals(dtoClientConfMain.getString("is_contain"))) {
                        map.put("uiconf_field", dtoClientConfMain.getString("uiconf_field"));
                        map.put("uiconf_title", dtoClientConfMain.getString("uiconf_title"));
                        map.put("uiconf_value", dtoClientConfMain.getString("uiconf_value"));
                        map.put("uiconf_datatype", dtoClientConfMain.getString("uiconf_datatype"));
                    } else {
                        dsConfMain.remove(map);
                    }
                }
                return isexists;
            })) {
                if ("1".equals(dtoClientConfMain.getString("is_contain"))) {
                    Map map = new HashMap();
                    map.put("uiconf_id", dtoClientConfMain.getString("ui_detail_id"));
                    map.put("uiconf_field", dtoClientConfMain.getString("uiconf_field"));
                    map.put("uiconf_title", dtoClientConfMain.getString("uiconf_title"));
                    map.put("uiconf_value", dtoClientConfMain.getString("uiconf_value"));
                    map.put("uiconf_datatype", dtoClientConfMain.getString("uiconf_datatype"));
                    dsConfMain.add(map);
                }
            }
        }
        return OkTipMsg("更新成功");
    }

        /**
         * 更新缓冲表，存入缓冲区
         * @param inDto
         * @throws Exception
         */
    @RequestMapping("/update_uiconfanddetail_cache.htm")
    public @ResponseBody Dto update_uiconfanddetail_cache(@RequestParam Map<String, Object> inDto, String dirtydata) throws Exception {
        inDto.remove("dirtydata");
        List<Map> dsDetail =uiManagerService.getPoolCache(new Object[]{"detail", httpSession.getId(), Convert.toStr(inDto.get("ui_id"))});
        Dto outDto = new HashDto();
        if (dsDetail!=null) {
            if (checkRepeatField(dsDetail,inDto)) {
                if (CommUtil.ContainsNode(dsDetail, (Map map) -> {
                    boolean isexists = Convert.toStr(map.get("ui_detail_id")).equals(Convert.toStr(inDto.get("ui_detail_id")));
                    if (isexists) {
                        map.putAll(inDto);
                    }
                    return isexists;
                })) {
                    List list  = JsonHandler.parseList(dirtydata);
                    List<Map> dsUiConfDetail =uiManagerService.getPoolCache(new Object[]{"confdetail", httpSession.getId(), (String) inDto.get("ui_detail_id")});
                    for (int i=0; i<list.size(); i++) {
                        Dto dtoClientConfDetail = (Dto) list.get(i);
                        if (!CommUtil.ContainsNode(dsUiConfDetail, (Map map) -> {
                            boolean isexists = map.get("uiconf_field").equals(dtoClientConfDetail.get("uiconf_field"));
                            if (isexists) {
                                if ("1".equals(dtoClientConfDetail.getString("is_contain"))) {
                                    map.put("uiconf_field", dtoClientConfDetail.getString("uiconf_field"));
                                    map.put("uiconf_title", dtoClientConfDetail.getString("uiconf_title"));
                                    map.put("uiconf_value", dtoClientConfDetail.getString("uiconf_value"));
                                    map.put("uiconf_datatype", dtoClientConfDetail.getString("uiconf_datatype"));
                                } else {
                                    dsUiConfDetail.remove(map);
                                }
                            }
                            return isexists;
                        })) {
                            if ("1".equals(dtoClientConfDetail.getString("is_contain"))) {
                                Map map = new HashMap();
                                map.put("uiconf_id", dtoClientConfDetail.getString("ui_detail_id"));
                                map.put("uiconf_field", dtoClientConfDetail.getString("uiconf_field"));
                                map.put("uiconf_title", dtoClientConfDetail.getString("uiconf_title"));
                                map.put("uiconf_value", dtoClientConfDetail.getString("uiconf_value"));
                                map.put("uiconf_datatype", dtoClientConfDetail.getString("uiconf_datatype"));
                                dsUiConfDetail.add(map);
                            }
                        }
                    }
                    if ("hidden".equals(inDto.get("field_type"))) {
                        inDto.put("iconCls", "nodeNoIcon");
                    } else {
                        boolean ishidden = CommUtil.ContainsNode(dsUiConfDetail, (Map map) -> {
                            if (map.get("uiconf_field").equals("hidden")) {
                                if ("true".equals(map.get("uiconf_value"))) {
                                    return true;
                                }
                            }
                            return false;
                        });
                        if (ishidden) {
                            inDto.put("iconCls", "nodeNoIcon");
                        } else {
                            inDto.put("iconCls", "nodeYesIcon");
                        }
                    }
                    outDto.put("data", inDto);
                    outDto.put("msg", "字段更新成功");
                    outDto.put("success", AeonConstants.TRUE);
                } else {
                    outDto.put("msg", "指定字段不存在，更新失败");
                    outDto.put("success", AeonConstants.FALSE);
                }
            } else {
                outDto.put("msg", "字段编码不允许重复，请检查");
                outDto.put("success", AeonConstants.FALSE);
            }
        }
        return outDto;
    }

    private boolean checkRepeatField(List<Map> dsDetail,Map inDto) throws Exception {
        for (Map map : dsDetail) {
            if (!Convert.toStr(map.get("ui_detail_id")).equals(Convert.toStr(inDto.get("ui_detail_id")))
                    && map.get("field_name").equals(inDto.get("field_name"))) {
                return false;
            }
        }
        return true;
    }

    @SecurityMapping(title = "界面视图新增",value = "ui:insert")
    @RequestMapping("/insert_uiitem.htm")
    public @ResponseBody Dto insert_uiitem(@RequestParam Map<String, Object> map) throws Exception {
        UIManager uimanager = WebHandler.toPo(map,UIManager.class);
        UIManager uiparent = uiManagerService.getObjByProperty(null, "ui_code", uimanager.getUi_code().substring(0,uimanager.getUi_code().length()-3));
        uimanager.setParent_id(uiparent==null ? null : uiparent.getId());
        uimanager.setAddTime(new Date());
        uimanager.setRg_code(SecurityUserHolder.getRgCode());
        Dto outDto = new HashDto();
        List<Map> dsUiConfMain = uiManagerService.getPoolCache(new Object[]{"confmain", httpSession.getId(), uimanager.getUi_id()});
        for (Map mapUiConfMain : dsUiConfMain) {
            UIConfMain uiconfmain = new UIConfMain();
            WebHandler.toPo(mapUiConfMain, uiconfmain);
            uiconfmain.setId(null);
            uimanager.getConfs().add(uiconfmain);
            uiconfmain.setUi_main(uimanager);
        }
        Map<String,?> mapUiDetail = uiManagerService.getPool(new Object[]{"detail", httpSession.getId()});
        for (Iterator<String> it = mapUiDetail.keySet().iterator(); it.hasNext();) {
            String key = it.next();
            List<Map> dsUiDetail = (List<Map>) mapUiDetail.get(key);
            for (Map mapDs : dsUiDetail) {
                UIDetail uidetail = new UIDetail();
                WebHandler.toPo(mapDs, uidetail);
                uidetail.setId(null);
                uimanager.getDetails().add(uidetail);
                uidetail.setUi(uimanager);
                List<Map> dsUiConfDetail = uiManagerService.getPoolCache(new Object[]{"confdetail", httpSession.getId(), uidetail.getUi_detail_id()});
                for (Map mapUiConfDetail : dsUiConfDetail) {
                    UIConfDetail uiconfdetail = new UIConfDetail();
                    WebHandler.toPo(mapUiConfDetail, uiconfdetail);
                    uiconfdetail.setId(null);
                    uidetail.getConfs().add(uiconfdetail);
                    uiconfdetail.setUi_detail(uidetail);
                }
            }
        }
        uiManagerService.save(uimanager);
        outDto.put("parent_id", uimanager.getParent_id());
        outDto.put("msg", "界面视图新增成功");
        outDto.put("success", AeonConstants.TRUE);
        return outDto;
    }

    @SecurityMapping(title = "界面视图修改",value = "ui:update")
    @RequestMapping("/update_uiitem.htm")
    public @ResponseBody Dto update_uiitem(@RequestParam Map<String, Object> map,@RequestParam("ui_id") Long id) throws Exception {
        UIManager uimanager = uiManagerService.getObjById(id);
        WebHandler.toPo(map,uimanager);
        UIManager uiparent = uiManagerService.getObjByProperty(null, "ui_code", uimanager.getUi_code().substring(0,uimanager.getUi_code().length()-3));
        uimanager.setParent_id(uiparent==null ? null : uiparent.getId());
        uimanager.setAddTime(new Date());
        uimanager.setRg_code(SecurityUserHolder.getRgCode());
        Dto outDto = new HashDto();
        List<Map> dsUiConfMain = uiManagerService.getPoolCache(new Object[]{"confmain", httpSession.getId(), uimanager.getUi_id()});
        for (UIConfMain uiConfMain : disposeRecordState(uimanager.getConfs(),dsUiConfMain,UIConfMain.class)) {
            uiConfMain.setUi_main(uimanager);
        }
        List<Map> dsUiDetail = uiManagerService.getPoolCache(new Object[]{"detail", httpSession.getId(),Convert.toStr(id)});
        for (UIDetail uidetail : disposeRecordState(uimanager.getDetails(),dsUiDetail,UIDetail.class)) {
            uidetail.setUi(uimanager);
            List<Map> dsUiConfDetail = uiManagerService.getPoolCache(new Object[]{"confdetail", httpSession.getId(), uidetail.getUi_detail_id()});
            for (UIConfDetail uiconfdetail : disposeRecordState(uidetail.getConfs(),dsUiConfDetail,UIConfDetail.class)) {
                uiconfdetail.setUi_detail(uidetail);
            }
        }
        uiManagerService.update(uimanager);
        outDto.put("parent_id", uimanager.getParent_id());
        outDto.put("msg", "界面视图修改成功");
        outDto.put("success", AeonConstants.TRUE);
        return outDto;
    }

    /**
     * 处理明细列记录状态
     * @param details
     * @param maps
     * @return
     */
    private <T> List<T> disposeRecordState(List<T> details, List<Map> maps, Class<T> clazz) {
        Iterator<T> iterator = details.iterator();
        while (iterator.hasNext()) {
            T detail = iterator.next();
            BeanWrapper wrapper = new BeanWrapperImpl(detail);
            if (!CommUtil.ContainsNode(maps,(Map map) -> {
                if (map.get("id")!=null) {
                    if (Convert.toStr(map.get("id")).equals(Convert.toStr(wrapper.getPropertyValue("id")))) {
                        //修改状态
                        WebHandler.toPo(map, detail);
                        return true;
                    }
                }
                return false;
            })) {
                //wrapper.setPropertyValue("is_deleted",1);
                iterator.remove();
            }
        }
        for (Map map : maps) {
            boolean isexists =false;
            if (map.get("id")!=null) {
                for (T detail : details) {
                    BeanWrapper wrapper = new BeanWrapperImpl(detail);
                    if (Convert.toStr(map.get("id")).equals(Convert.toStr(wrapper.getPropertyValue("id")))) {
                        isexists = true;
                    }
                }
            }
            if (!isexists) {
                map.remove("id");
                T obj = WebHandler.toPo(map, clazz);
                details.add(obj);
            }
        }
        return details;
    }

    @SecurityMapping(title = "界面视图删除",value = "ui:delete")
    @RequestMapping("/delete_uiitem.htm")
    public @ResponseBody Dto delete_uiitem(@RequestParam("ui_id") Long id) throws Exception {
        this.uiManagerService.delete(id);
        return OkTipMsg("界面视图删除成功");
    }

    @RequestMapping("ui_designer.htm")
    public @ResponseBody String ui_designer(@RequestParam Map<String, Object> map) throws Exception{
        return baseUIService.getCache(new Object[]{map});
    }

    @RequestMapping("uimain_designer.htm")
    public @ResponseBody String uimain_designer(String comp_id, String servletpath, Long ui_id) throws Exception{
        QueryObject qo = new QueryObject();
        if (CommUtil.isNotEmpty(comp_id)) {
            qo.addQuery("obj.ui_main.comp_id", new SysMap("comp_id", comp_id), "=");
        }
        if (CommUtil.isNotEmpty(servletpath)) {
            qo.addQuery("obj.ui_main.servletpath", new SysMap("servletpath", servletpath), "=");
        }
        if (CommUtil.isNotEmpty(ui_id)) {
            qo.addQuery("obj.ui_main.id", new SysMap("ui_id", ui_id), "=");
        }
        List<UIConfMain> elements = this.uiManagerService.findConfMain(qo);
        if (elements!=null && elements.size()>0) {
            return JsonHandler.toJson(elements.get(0));
        } else {
            return "[]";
        }
    }

    @RequestMapping("/ui_designer1.htm")
    public @ResponseBody String ui_designer1(String comp_id, String servletpath, Long ui_id) {
        QueryObject qo = new QueryObject("ui_code","asc");
        //qo.setFetchs(" join fetch obj.details as det join fetch det.confs");
        if (CommUtil.isNotEmpty(comp_id)) {
            qo.addQuery("obj.comp_id", new SysMap("comp_id", comp_id), "=");
        }
        if (CommUtil.isNotEmpty(servletpath)) {
            qo.addQuery("obj.servletpath", new SysMap("servletpath", servletpath), "=");
        }
        if (CommUtil.isNotEmpty(ui_id)) {
            qo.addQuery("obj.id", new SysMap("ui_id", ui_id), "=");
        }
        List<UIManager> elements = this.uiManagerService.find(qo);
        if (elements!=null && elements.size()>0) {
//            Map<String,String> mapInclude = new HashMap<>();
//            mapInclude.put(UIManager.class.getName(),"id,ui_code,ui_name,details");
//            mapInclude.put(UIDetail.class.getName(),"id,field_name,field_index,confs");
//            mapInclude.put(UIConfDetail.class.getName(),"id,uiconf_field,uiconf_value");
            return JsonHandler.toJson(elements.get(0).getDetails());
        } else {
            return "[]";
        }
    }

    @RequestMapping("/ui_ele_designer.htm")
    public @ResponseBody String ui_ele_designer(String source) throws Exception {
        if (CommUtil.isNotEmpty(source)) {
            Element ele = elementService.getObjByProperty(null, "ele_code", source);
            if (ele!=null) {
                Map map = new HashMap();
                map.put("ui_id",ele.getUi_id());
                return baseUIService.getCache(new Object[]{map});
            }
        }
        return "[]";
    }

    @RequestMapping("/ui_sync_memory.htm")
    public @ResponseBody void ui_sync_memory() {
        this.uiManagerService.reset();
    }

}
