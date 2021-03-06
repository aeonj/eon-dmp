package eon.hg.fap.db.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONReader;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.domain.virtual.SysMap;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.db.dao.primary.*;
import eon.hg.fap.db.model.primary.*;
import eon.hg.fap.db.service.IResourceLoaderService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class ResourceLoaderServiceImpl implements IResourceLoaderService {

    @Resource
    private UIConfDao uiConfDAO;
    @Resource
    private UIConfDetailDao uiConfDetailDAO;
    @Resource
    private UIDetailDao uiDetailDAO;
    @Resource
    private UIConfMainDao uiConfMainDAO;
    @Resource
    private UIManageDao uiManageDAO;
    @Resource
    private MenuGroupDao menuGroupDAO;
    @Resource
    private MenuDao menuDAO;
    @Resource
    private OperateDao operateDAO;
    @Resource
    private ElementDao elementDAO;
    @Resource
    private EnumerateDao enumerateDAO;
    @Resource
    private IconsDao iconsDao;

    @Override
    public void initSysTable() throws Exception {
        //初始化界面视图
        String path = ResourceUtils.getURL("classpath:").getPath();

        impTableJsonData(getClassPathStream("eon/hg/fap/data/json/sys_uiconf.json"), (Map<String, Object> mapRecord) -> {
            UIConf uiConf = WebHandler.toPo(mapRecord, UIConf.class);
            UIConf uvf = uiConfDAO.getOne("uiconf_type", uiConf.getUiconf_type(),"uiconf_field",uiConf.getUiconf_field());
            if (uvf==null) {
                uiConf.setId(null);
            } else {
                uiConf.setId(uvf.getId());
            }
            uiConfDAO.mergeSave(uiConf);
        });
        List<UIManager> uiManagerList = new ArrayList<>();
        impTableJsonData(getClassPathStream("eon/hg/fap/data/json/sys_uimanager.json"), (Map<String, Object> mapRecord) -> {
            UIManager uiManager = BeanUtil.mapToBeanIgnoreCase(mapRecord, UIManager.class, true);
            impTableJsonData(getClassPathStream("eon/hg/fap/data/json/sys_uidetail.json"), (Map<String, Object> mapDetail) -> {
                if (mapDetail.get("ui_id").equals(mapRecord.get("id"))) {
                    UIDetail uiDetail = BeanUtil.mapToBeanIgnoreCase(mapDetail, UIDetail.class, true);
                    impTableJsonData(getClassPathStream("eon/hg/fap/data/json/sys_uiconf_detail.json"), (Map<String, Object> mapConfDetail) -> {
                        UIConfDetail uiConfDetail = BeanUtil.mapToBeanIgnoreCase(mapConfDetail, UIConfDetail.class, true);
                        if (mapConfDetail.get("ui_detail_id").equals(mapDetail.get("id"))) {
                            uiDetail.getConfs().add(uiConfDetail);
                            uiConfDetail.setId(null);
                            uiConfDetail.setUi_detail(uiDetail);
                        }
                    });
                    uiManager.getDetails().add(uiDetail);
                    uiDetail.setId(null);
                    uiDetail.setUi(uiManager);
                }

            });
            uiManager.setParent_id(null);
            uiManager.setId(null);
            uiManagerList.add(uiManager);
        });
        Collections.sort(uiManagerList, new Comparator<UIManager>() {
            public int compare(UIManager arg0, UIManager arg1) {
                String hits0 = arg0.getUi_code();
                String hits1 = arg1.getUi_code();
                if (hits1.compareTo(hits0)<0) {
                    return 1;
                } else if (hits1 == hits0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        for (UIManager uiManager : uiManagerList) {
            UIManager vf = uiManageDAO.getOne("ui_code",uiManager.getUi_code());
            if (vf!=null) {
                uiManager.setId(vf.getId());
            }
            UIManager uiparent = uiManageDAO.getOne("ui_code", uiManager.getUi_code().substring(0, uiManager.getUi_code().length() - 3));
            uiManager.setParent_id(uiparent == null ? null : uiparent.getId());
            uiManageDAO.mergeSave(uiManager);
        }

        //初始化菜单
        impTableJsonData(getClassPathStream("eon/hg/fap/data/json/sys_menugroup.json"), (Map<String, Object> mapRecord) -> {
            MenuGroup menuGroup = BeanUtil.mapToBeanIgnoreCase(mapRecord, MenuGroup.class, true);
            MenuGroup vf =menuGroupDAO.getOne("name",menuGroup.getName());
            if (vf!=null) {
                menuGroup.setId(vf.getId());
            } else {
                menuGroup.setId(null);
                menuGroupDAO.save(menuGroup);
            }
            Stack<Menu> stack = new Stack();
            impTableJsonData(getClassPathStream("eon/hg/fap/data/json/sys_menu.json"), (Map<String, Object> mapMenu) -> {
                if (mapMenu.get("mg_id").equals(mapRecord.get("id"))) {
                    Menu menu = BeanUtil.mapToBeanIgnoreCase(mapMenu, Menu.class, true);
                    menu.setMg(menuGroup);

                    //增加操作导入 add by eonook 2020.3.22
                    impTableJsonData(getClassPathStream("eon/hg/fap/data/json/sys_operate.json"), (Map<String, Object> mapOperate) -> {
                        if (mapOperate.get("menu_id").equals(mapMenu.get("id"))) {
                            Operate operate = BeanUtil.mapToBeanIgnoreCase(mapOperate, Operate.class, true);
                            operate.setMenu(menu);
                            menu.getOperates().add(operate);
                        }
                    });

                    if (CommUtil.isEmpty(mapMenu.get("parent_id"))) {
                        stack.push(menu);
                    } else {
                        menuGroup.getMenus().add(menu);
                    }
                }
            });

            while (!stack.isEmpty()) {
                Menu menu = stack.pop();
                System.out.println(menu.getMenuCode() + menu.getMenuName());
                Long parent_id = menu.getId();
                Menu mvf = menuDAO.getOne("menuCode",menu.getMenuCode());
                if (mvf!=null) {
                    menu.setId(mvf.getId());
                } else {
                    menu.setId(null);
                }
                List<Operate> operateList = new ArrayList<>();
                operateList.addAll(menu.getOperates());
                menu.getOperates().clear();
                Menu save = menuDAO.mergeSave(menu);

                //增加操作导入 add by eonook 2020.3.22
                for (Operate operate : operateList) {
                    System.out.println("菜单信息："+menu.getMenuCode() + menu.getMenuName()+"  菜单操作："+operate.getName());
                    QueryObject qo = new QueryObject();
                    qo.addQuery("obj.menu.id", new SysMap("menu_id", menu.getId()), "=");
                    qo.addQuery("obj.name", new SysMap("name", operate.getName()), "=");
                    List<Operate> vfs = this.operateDAO.find(qo);
                    if (vfs == null || vfs.size() == 0) {
                        operate.setId(null);
                    } else {
                        operate.setId(vfs.get(0).getId());
                    }
                    this.operateDAO.mergeSave(operate);
                }

                //处理
                List<Menu> childs = CollectionUtil.filter(menuGroup.getMenus(), (Menu child) -> (
                        child.getParent_id().longValue() == parent_id.longValue()
                ));
                for (Menu child : childs) {
                    child.setParent_id(save.getId());
                    stack.push(child);
                    menuGroup.getMenus().remove(child);
                }
            }
        });

        //初始化要素
        impTableJsonData(getClassPathStream("eon/hg/fap/data/json/sys_element.json"), (Map<String, Object> mapRecord) -> {
            Element obj = BeanUtil.mapToBeanIgnoreCase(mapRecord, Element.class, true);
            Element vf = elementDAO.getOne("ele_code",obj.getEle_code());
            if (vf==null) {
                obj.setId(null);
            } else {
                obj.setId(vf.getId());
            }
            elementDAO.mergeSave(obj);
        });

        //初始化枚举值
        impTableJsonData(getClassPathStream("eon/hg/fap/data/json/sys_enumerate.json"), (Map<String, Object> mapRecord) -> {
            Enumerate obj = BeanUtil.mapToBeanIgnoreCase(mapRecord, Enumerate.class, true);
            Enumerate vf = enumerateDAO.getOne("field",obj.getField(),"code",obj.getCode());
            if (vf==null) {
                obj.setId(null);
            } else {
                obj.setId(vf.getId());
            }
            enumerateDAO.mergeSave(obj);
        });

        //初始化图标值
        impTableJsonData(getClassPathStream("eon/hg/fap/data/json/sys_icons.json"), (Map<String, Object> mapRecord) -> {
            Icons obj = BeanUtil.mapToBeanIgnoreCase(mapRecord,Icons.class,true);
            Icons vf = iconsDao.getOne("icons",obj.getIcons());
            if (vf==null) {
                obj.setId(null);
            } else {
                obj.setId(vf.getId());
            }
            iconsDao.mergeSave(obj);
        });


    }

    @Override
    public void upgrateSysTable() throws Exception {

    }

    private void impTableJsonData(InputStream in, ImpJsonData impJsonData) throws Exception {
        //读取文件上的数据。  
        // 将字节流向字符流的转换。  
        InputStreamReader isr = new InputStreamReader(in, "UTF-8");//读取  
        JSONReader reader = new JSONReader(isr);
        reader.startObject();
        System.out.println("开始解析JSON文件");
        while (reader.hasNext()) {
            String key = reader.readString();
            System.out.println("开始解析关键字：" + key);
            if (key.equals("RECORDS")) {
                reader.startArray();
                System.out.println("start " + key);
                while (reader.hasNext()) {
                    reader.startObject();
                    System.out.println("start arraylist item");
                    Map<String, Object> mapRecord = new HashMap<>();
                    while (reader.hasNext()) {
                        String arrayListItemKey = reader.readString();
                        Object arrayListItemValue = reader.readObject();
                        System.out.print("key " + arrayListItemKey);
                        System.out.print("value " + arrayListItemValue);
                        mapRecord.put(arrayListItemKey.toLowerCase(), arrayListItemValue);
                    }
                    impJsonData.insertTable(mapRecord);

                    reader.endObject();
                    System.out.println("end arraylist item");
                }
                reader.endArray();
                System.out.println("结束解析关键字：" + key);
            } else if (key.equals("TABLE")) {

            }
        }
        reader.endObject();
        System.out.println("结束解析JSON文件");
    }

    public Map<String, Object> getInsertSql(String table_name, Map inMap) {
        Map map = new HashMap();
        map.putAll(inMap);
        StringBuffer field = new StringBuffer();
        StringBuffer value = new StringBuffer();
        List objs = new ArrayList();
        Iterator keyIt = map.keySet().iterator();
        while (keyIt.hasNext()) {
            final String fieldInMap = (String) keyIt.next();
            final Object valueInMap = map.get(fieldInMap);
            field.append(fieldInMap).append(",");
            if (valueInMap != null) {
                if ("parent_id".equalsIgnoreCase(fieldInMap)) {
                    objs.add("".equals(valueInMap) ? null : valueInMap);
                } else if ("is_deleted".equalsIgnoreCase(fieldInMap)) {
                    objs.add(0);
                } else {
                    objs.add(StrUtil.containsIgnoreCase(fieldInMap, "Time") ? null : valueInMap);
                }
                value.append("?,");
            } else {
                objs.add(null);
                value.append("?,");
            }
        }
        field.deleteCharAt(field.length() - 1);
        value.deleteCharAt(value.length() - 1);
        StringBuffer strSQL = new StringBuffer();
        strSQL.append("insert into ").append(table_name).append(" (").append(field).append(") ")
                .append("values (").append(value).append(")");
        Map map1 = new HashMap();
        map1.put("sql", strSQL.toString());
        map1.put("objs", ArrayUtil.toArray(objs, Object.class));
        return map1;
    }

    private interface ImpJsonData {
        void insertTable(Map<String, Object> mapRecord) throws Exception;
    }

    private class SqlAndParams {
        String sql;
        Object[] objs;
    }

    private InputStream getClassPathStream(String filestr) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(filestr);
        return classPathResource.getInputStream();
    }
}
