/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2017年7月7日
 * @author cxj
 */

package eon.hg.fap.common.tools.json;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 过滤掉类里面的字段
 * 
 * @author aeon
 *
 */
public class JsonExcludes {

	/**
	 * 排除bean之间的关系字段
	 * 
	 * @param bean
	 * @return
	 */
	public static String[] getExcludeFields(Class clzz, String includes) {
		List<String> lst_includes = Arrays.asList(StringUtils.commaDelimitedListToStringArray(includes));
		Set<String> list = new HashSet<String>();
		// list.add("handler");
		// list.add("hibernateLazyInitializer");
		for (Class<?> superClass = clzz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for (Field field : fields) {
				if (!lst_includes.contains(field.getName())) {
					list.add(field.getName());
				}
			}
		}
		return list.toArray(new String[list.size()]);
	}

}
