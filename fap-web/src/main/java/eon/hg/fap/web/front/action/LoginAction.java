package eon.hg.fap.web.front.action;

import eon.hg.fap.common.tools.json.JsonIncludePreFilter;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.model.mapper.BaseData;
import eon.hg.fap.db.model.primary.EleDepartment;
import eon.hg.fap.db.model.primary.Menu;
import eon.hg.fap.db.service.IBaseDataService;
import eon.hg.fap.db.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginAction {

    @Autowired
    private IMenuService userService;
    @Autowired
    private IBaseDataService baseDataService;

    /**
     * springsecurity登录成功后跳转地址，如有登录相关处理可以在该方法中完成
     *
     * @return
     */
    @RequestMapping("/user_login_success.htm")
    public String user_login_success() {
        return "redirect:/index.htm";
    }

    @RequestMapping("/cxj.htm")
    public List<BaseData> user_list() {
            QueryObject qo = new QueryObject();
            List<BaseData> bds = baseDataService.find(EleDepartment.class,qo);
            for (BaseData bd:bds) {
                System.out.println(baseDataService.getObjById(bd.getClass(),bd.getParent_id()));
            }
            return bds;
    }

    @RequestMapping("/cxj1.htm")
    public String user_list1() {
        QueryObject qo = new QueryObject();
        return JsonHandler.toResultJson(this.userService.list(qo),new JsonIncludePreFilter(Menu.class,"id","addTime","menuName"));
    }

}
