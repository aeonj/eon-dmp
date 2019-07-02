/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2017年7月7日
 * @author cxj
 */

package eon.hg.fap.common.tools.json;

import com.alibaba.fastjson.serializer.PropertyFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 对于jpa的懒加载，非ehcache缓存的在这里进行急加载
 * @author aeon
 *
 */
public class FastJsonPropertyFilterImpl implements PropertyFilter {

	private static Log log = LogFactory.getLog(FastJsonPropertyFilterImpl.class);
	private Map<String, String> match = null;

	public FastJsonPropertyFilterImpl() {

	}

	public FastJsonPropertyFilterImpl(Map<String,String> match) {
		this.match  = match;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.json.util.PropertyFilter#apply(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean apply(Object source, String name, Object value) {
		if (this.match==null) {
			if (value instanceof PersistentBag) {
				if (!Hibernate.isInitialized(value)) {
					return true;
				}
			}
			if (value instanceof HibernateProxy) {
				if (!Hibernate.isInitialized(value)) {
					return true;
				}
			}
		} else {
			if (value instanceof PersistentBag || value instanceof HibernateProxy) {
				String clzzname = source.getClass().getName();
				if (this.match.containsKey(clzzname)) {
					List<String> lst_includes = Arrays.asList(StringUtils.commaDelimitedListToStringArray(this.match.get(clzzname)));
					if (lst_includes.contains(name)) {
						return false;
					} else {
						return true;
					}
				} else {
					if (!Hibernate.isInitialized(value)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
