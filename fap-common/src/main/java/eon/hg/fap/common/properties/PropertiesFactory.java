package eon.hg.fap.common.properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Properties文件静态工厂
 * 
 * @author AEON
 */
public class PropertiesFactory {
	private static Log log = LogFactory.getLog(PropertiesFactory.class);

	/**
	 * 属性文件实例容器
	 */
	private static Map<String, PropertiesHelper> container = new HashMap<String,PropertiesHelper>();

	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = PropertiesFactory.class.getClassLoader();
		}
		// 加载属性文件config.properties
		try {
			InputStream is = classLoader.getResourceAsStream(PropertiesFile.CONFIG_FILE);
			PropertiesHelper ph = new PropertiesHelper(is);
			container.put(PropertiesFile.CONFIG, ph);
		} catch (Exception e1) {
			log.error("加载属性文件"+PropertiesFile.CONFIG_FILE+"出错!");
			e1.printStackTrace();
		}
		// 加载属性文件manage.properties
		try {
			InputStream is = classLoader.getResourceAsStream(PropertiesFile.MANAGE_FILE);
			PropertiesHelper ph = new PropertiesHelper(is);
			container.put(PropertiesFile.MANAGE, ph);
		} catch (Exception e1) {
			log.error("加载属性文件"+PropertiesFile.MANAGE_FILE+"出错!");
			e1.printStackTrace();
		}
		// 加载属性文件manage.properties
		try {
			InputStream is = classLoader.getResourceAsStream(PropertiesFile.LICENSE_FILE);
			PropertiesHelper ph = new PropertiesHelper(is);
			container.put(PropertiesFile.LICENSE, ph);
		} catch (Exception e1) {
			log.error("加载属性文件"+PropertiesFile.LICENSE_FILE+"出错!");
			e1.printStackTrace();
		}
	}

	/**
	 * 获取属性文件实例
	 * 
	 * @param pFile
	 *            文件类型
	 * @return 返回属性文件实例
	 */
	public static PropertiesHelper getPropertiesHelper(String pFile) {
		PropertiesHelper ph = container.get(pFile);
		return ph;
	}
}
