package eon.hg.fap.core.listener;

import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.properties.PropertiesBean;
import eon.hg.fap.common.util.tools.FileHandler;
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
    @Autowired
    PropertiesBean propertiesBean;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {

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
}
