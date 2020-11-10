package eon.hg.fap.core.listener;

import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.properties.PropertiesBean;
import eon.hg.fap.common.util.tools.FileHandler;
import eon.hg.fap.core.cache.RedisPool;
import eon.hg.fap.core.constant.AeonConstants;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by aeon.
 * 系统启动监听器
 */
@WebListener
public class SystemInitListener implements ServletContextListener {
    @Autowired(required = false)
    private RedisPool redisPool;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            if (redisPool!=null) {
                clearRedisCache();
            }
            String path = PropertiesBean.UPLOAD_FOLDER;
            if (StrUtil.isBlank(path)) {
                path = FileHandler.getLicenseFilePath();
            }
            //String path = ResourceUtils.getURL("classpath:").getPath();
            AeonConstants.VLicense.install(path);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    private void clearRedisCache() {
        if (redisPool.hasKey("ui_view")) {
            redisPool.del("ui_view");
        }
        if (redisPool.hasKey("sys_config")) {
            redisPool.del("sys_config");
        }
        if (redisPool.hasKey("user_config")) {
            redisPool.del("user_config");
        }
        if (redisPool.hasKey("ele_table_values")) {
            redisPool.del("ele_table_values");
        }
    }
}
