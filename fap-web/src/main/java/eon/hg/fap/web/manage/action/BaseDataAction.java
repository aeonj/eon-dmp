package eon.hg.fap.web.manage.action;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.body.PageBody;
import eon.hg.fap.core.body.ResultBody;
import eon.hg.fap.core.body.ResultCode;
import eon.hg.fap.core.domain.virtual.SysMap;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.db.model.mapper.BaseData;
import eon.hg.fap.db.model.primary.Element;
import eon.hg.fap.db.service.IBaseDataService;
import eon.hg.fap.db.service.IBaseTreeService;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import eon.hg.fap.security.annotation.SecurityMapping;
import eon.hg.fap.web.manage.op.ElementOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class BaseDataAction {
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IBaseDataService baseDataService;
    @Autowired
    private IBaseTreeService baseTreeService;
    @Autowired
    private ElementOP elementOP;

    @SecurityMapping(title = "通用基础数据管理", value = "base_data:view")
    @RequestMapping("/base_data.htm")
    public ModelAndView base_data(HttpServletRequest request,
                                  HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("fap/base_data.html",
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        mv.addObject("ele_op",elementOP);
        return mv;
    }

    @SecurityMapping("base_data:view")
    @RequestMapping("/basedata_list.htm")
    public PageBody basedata_list(String source, String code, int page, int limit) throws ClassNotFoundException {
        if (StrUtil.isNotBlank(source)) {
            Element ele = elementOP.getEleSource(source);
            if (StrUtil.isNotBlank(ele.getClass_name())) {
                QueryObject qo = new QueryObject(null, "code", "asc", page, limit);
                qo.addQuery(" and obj.code like '"+CommUtil.null2String(code)+"%'",null);
                IPageList pageList = this.baseDataService.list(Class.forName(ele.getClass_name()), qo);
                return PageBody.success().addPageInfo(pageList);
            } else {
                QueryObject qo = QueryObject.SqlCreate("SELECT obj.* from "+ele.getEle_source()+" obj",false);
                qo.addQuery("code",new SysMap("code", CommUtil.null2String(code)+"%"),"like");
                IPageList pageList = this.baseDataService.list(null,qo);
                return PageBody.success().addPageInfo(pageList);
            }

        } else {
            return PageBody.failed("数据源未定义");
        }
    }

    @SecurityMapping(title = "基础数据新增", value = "base_data:insert")
    @RequestMapping("basedata_insert.htm")
    public ResultBody basedata_insert(@RequestParam Map<String, Object> mapPara, @RequestParam("element_code") String source) {
        Element ele = elementOP.getEleSource(source);
        try {
            Class<BaseData> clz = (Class<BaseData>) Class.forName(ele.getClass_name());

            BaseData baseData = WebHandler.toPo(mapPara,clz);
            BaseData vf = this.baseDataService.getObjByProperty(baseData.getClass(), "code", baseData.getCode());
            if (vf != null) {
                return ResultBody.failed("编码不能重复");
            } else {
                baseData.setId(null);
                baseData.setAddTime(new Date());
                this.baseDataService.save(baseData);
            }
            return ResultBody.success();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return ResultBody.failed("要素类名未定义");
        }
    }

    @SecurityMapping(title = "基础数据修改", value = "base_data:update")
    @RequestMapping("basedata_update.htm")
    public ResultBody basedata_update(@RequestParam Map<String, Object> mapPara, @RequestParam("element_code") String source, Long id, String code) {
        Element ele = elementOP.getEleSource(source);
        try {
            Class<BaseData> clz = (Class<BaseData>) Class.forName(ele.getClass_name());
            BaseData vf = this.baseDataService.getObjByProperty(clz,  "code", code);
            if (vf != null && !vf.getId().equals(id)) {
                return ResultBody.failed("编码不能重复");
            } else {
                BaseData baseData = this.baseDataService.getObjById(clz,id);
                baseData.setOld_parent_id(baseData.getParent_id());
                BaseData obj = WebHandler.toPo(mapPara, baseData);
                //BaseData obj = BeanUtil.fillBeanWithMapIgnoreCase(mapPara,baseData,false);
                this.baseDataService.update(obj);
                return ResultBody.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBody.failed("要素类名未定义");
        }
    }


    @SecurityMapping(title = "基础数据删除", value = "base_data:delete")
    @RequestMapping("basedata_delete.htm")
    public ResultBody basedata_delete(@RequestParam("element_code") String source, @RequestParam("ids") List<Long> idlist) {
        Element ele = elementOP.getEleSource(source);
        try {
            Class<BaseData> clz = (Class<BaseData>) Class.forName(ele.getClass_name());
            this.baseDataService.batchDelete(clz, idlist);
            return ResultBody.success();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return ResultBody.failed("要素类名未定义");
        }
    }

    /**
     * Excel导入基础数据
     *     注明：只支持Excel标题栏设置别名的Excel模板，设置的别名与基础数据字段对应
     * @param file Excel文件
     * @param source 要素名称
     * @return
     */
    @SecurityMapping(title = "基础数据导入", value = "base_data:import")
    @RequestMapping("basedata_imp_alias.htm")
    public ResultBody basedata_imp_alias(@RequestParam("file") MultipartFile file, @RequestParam("source") String source) {
        try {
            ExcelReader excelReader = ExcelUtil.getReader(file.getInputStream());
            List<Map<String,Object>> rowsList = excelReader.readAll();

            Element ele = elementOP.getEleSource(source);
            Class<BaseData> clz = (Class<BaseData>) Class.forName(ele.getClass_name());

            for (Map<String,Object> rowMap : rowsList) {
                BaseData baseData = WebHandler.toPo(rowMap,clz);
                if (rowMap.containsKey("parent_code")) {
                    BaseData bd = elementOP.getBaseDataByCode(source,CommUtil.null2String(rowMap.get("parent_code")));
                    if (bd==null) {
                        baseData.setParent_id(null);
                    } else {
                        baseData.setParent_id(bd.getId());
                    }
                }
                baseData.setId(null);
                baseData.setAddTime(new Date());
                baseDataService.save(baseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBody.failed(ResultCode.FAILED);
        }
        return ResultBody.success();
    }

    /**
     * Excel导入基础数据
     *     注明：1、Excel第一行标题栏设置为字段名，优先使用
     *          2、Excel第一行标题栏设置为非字段名，根据请求传回的metadata变量确定Excel对应的字段名
     *          3、请求传回的metadata变量，不传或空值则固定为code,name,parent_code
     * @param file excel文件
     * @param source 要素名称
     * @param metadata 传回的逗号分隔的字段列表
     * @return
     */
    @SecurityMapping(title = "基础数据导入", value = "base_data:import")
    @RequestMapping("basedata_imp.htm")
    public ResultBody basedata_imp(@RequestParam("file") MultipartFile file, @RequestParam("source") String source,
            String metadata) {
        if (StrUtil.isBlank(metadata)) {
            metadata = "code,name,parent_code";
        }
        try {
            ExcelReader excelReader = ExcelUtil.getReader(file.getInputStream());
            String[] metaDatas = StrUtil.split(metadata,",");
            List<List<Object>> cellsList = excelReader.read();

            Element ele = elementOP.getEleSource(source);
            Class<BaseData> clz = (Class<BaseData>) Class.forName(ele.getClass_name());

            boolean isFirst = true;
            boolean isAlias = false;
            for (List<Object> rowList : cellsList) {
                if (isFirst) {
                    List<String> fields = new ArrayList<>();
                    for (Object row : rowList) {
                        if (!isAlias && "code".equals(row)) {
                            isAlias = true;
                        }
                        fields.add(CommUtil.null2String(row));
                    }
                    if (isAlias) {
                        metaDatas = ArrayUtil.toArray(fields,String.class);
                    }
                    isFirst = false;
                    continue;
                }
                Dto rowDto = new HashDto();
                for (int i=0; i<metaDatas.length; i++) {
                    rowDto.put(metaDatas[i],rowList.get(i));
                }
                BaseData baseData = WebHandler.toPo(rowDto,clz);
                if (rowDto.containsKey("parent_code")) {
                    BaseData bd = elementOP.getBaseDataByCode(source,StrUtil.toString(rowDto.get("parent_code")));
                    if (bd==null) {
                        baseData.setParent_id(null);
                    } else {
                        baseData.setParent_id(bd.getId());
                    }
                }
                baseData.setId(null);
                baseData.setAddTime(new Date());
                baseDataService.save(baseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBody.failed(ResultCode.FAILED);
        }
        return ResultBody.success();
    }

    @RequestMapping("/basedata_sync_memory.htm")
    public @ResponseBody void basedata_sync_memory() {
        this.baseTreeService.reset();
    }


}
