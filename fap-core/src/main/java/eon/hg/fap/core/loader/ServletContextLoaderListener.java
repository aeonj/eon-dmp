package eon.hg.fap.core.loader;

import eon.hg.fap.core.jpa.EonDao;
import eon.hg.fap.core.constant.AeonConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 系统信息初始化加载，加载权限，语言等信息，启动时配置用
 * @author apple
 *
 */
public class ServletContextLoaderListener implements ServletContextListener {
	
	private static Log log = LogFactory.getLog(ServletContextLoaderListener.class);

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		EonDao geDao = getEntityDao(servletContext);
		try {
			initDbType(geDao);
		} catch (SQLException e) {
			e.printStackTrace();
			System.setProperty("aeonDao.db", "mysql");
		}
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		servletContextEvent.getServletContext().removeAttribute(
				"urlAuthorities");
	}

	protected EonDao getEntityDao(ServletContext servletContext) {
		return (EonDao) WebApplicationContextUtils
				.getWebApplicationContext(servletContext).getBean(
						"eonDao");
	}
	
	/**
	 * 识别缺省的JDBC驱动类型(aeonDao)
	 * 
	 * @throws SQLException
	 */
	private void initDbType(EonDao geDao) throws SQLException {
		Connection connection=geDao.getConnection();
		String dbString = connection.getMetaData().getDatabaseProductName().toLowerCase();
		if (dbString.indexOf("ora") > -1) {
			System.setProperty("aeonDao.db", "oracle");
		} else if (dbString.indexOf("mysql") > -1) {
			System.setProperty("aeonDao.db", "mysql");
		}else if (dbString.indexOf("microsoft") > -1) {
			System.setProperty("aeonDao.db", "sqlserver");
		} else {
			if (log.isErrorEnabled()) {
				log.error(AeonConstants.Exception_Head + "平台目前还不支持你使用的数据库产品.");
			}
			System.exit(0);
		}
	}

}
