package eon.hg.fap.core.listener;

import eon.hg.fap.core.constant.AeonConstants;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by aeon.
 * 系统启动监听器
 */
@WebListener
public class SystemInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            //String path = FileHandler.getLicenseFilePath();
            String path = ResourceUtils.getURL("classpath:").getPath();
            AeonConstants.VLicense.install(path);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
