package eon.hg.fap.web.manage.action;

import cn.hutool.core.util.StrUtil;
import eon.hg.fap.core.body.PageBody;
import eon.hg.fap.core.body.ResultBody;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.db.dao.primary.GenericDao;
import eon.hg.fap.db.model.mapper.BaseData;
import eon.hg.fap.db.model.primary.Element;
import eon.hg.fap.db.service.IBaseDataService;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import eon.hg.fap.security.annotation.SecurityMapping;
import eon.hg.fap.web.manage.op.ElementOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private ElementOP elementOP;
    @Autowired
    private GenericDao genericDao;

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
                IPageList pageList = this.baseDataService.list(Class.forName(ele.getClass_name()), qo);
                return PageBody.success().addPageInfo(pageList);
            } else {
                QueryObject qo = QueryObject.SqlCreate("SELECT obj.* from "+ele.getEle_source()+" obj where code= "+code);
                //qo.addQuery("code",new SysMap("code",code),"like");
                IPageList pageList = this.genericDao.list(qo);
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
            BaseData vf = this.baseDataService.getObjByProperty(baseData.getClass(), null, "code", baseData.getCode());
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
            BaseData vf = this.baseDataService.getObjByProperty(clz, null, "code", code);
            if (vf != null && !vf.getId().equals(id)) {
                return ResultBody.failed("编码不能重复");
            } else {
                BaseData baseData = this.baseDataService.getObjById(clz, id);
                BaseData obj = WebHandler.toPo(mapPara, baseData);
                this.baseDataService.update(obj);
                return ResultBody.success();
            }
        } catch (ClassNotFoundException e) {
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
}
