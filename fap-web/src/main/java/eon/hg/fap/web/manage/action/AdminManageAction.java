package eon.hg.fap.web.manage.action;

import eon.hg.fap.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage")
public class AdminManageAction {
    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IResourceLoaderService resourceLoaderService;
    @Autowired
    private IBaseTreeService baseTreeService;
    @Autowired
    private IBaseUIService baseUIService;

    @RequestMapping("/upgrate_table.htm")
    public void upgrate_table() throws Exception {
        resourceLoaderService.initSysTable();
        resourceLoaderService.upgrateSysTable();
    }

    @RequestMapping("/clear_redis_cache.htm")
    public void clear_redis_cache() {
        baseTreeService.reset();
        baseUIService.reset();
        sysConfigService.reset();
        userConfigService.reset();
    }
}
