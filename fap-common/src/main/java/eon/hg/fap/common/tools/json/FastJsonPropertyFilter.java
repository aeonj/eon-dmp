/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2017年7月7日
 * @author cxj
 */

package eon.hg.fap.common.tools.json;

import com.alibaba.fastjson.serializer.BeforeFilter;
import com.alibaba.fastjson.serializer.PropertyFilter;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;

/**
 * 对于jpa的懒加载，非ehcache缓存的在这里进行急加载
 * @author aeon
 *
 */
public class FastJsonPropertyFilter extends BeforeFilter implements PropertyFilter {

	@Override
	public boolean apply(Object source, String name, Object value) {
		if (value instanceof HibernateProxy) {//hibernate代理对象
			return false;

		} else if (value instanceof PersistentCollection) {//实体关联集合一对多等
			PersistentCollection collection = (PersistentCollection) value;
			if (!collection.wasInitialized()) {
				return false;
			}
			Object val = collection.getValue();
			if (val == null) {
				return false;
			}
		} else if (value instanceof DefaultOAuth2AccessToken
				|| value instanceof DefaultExpiringOAuth2RefreshToken) {
			return false;
		}
		return true;
	}

	@Override
	public void writeBefore(Object object) {
		/**
		 * DefaultOAuth2AccessToken 默认格式
		 * {
		 *     "access_token": "763d17ee-7847-4d8b-b3e8-207110d094c9",
		 *     "token_type": "bearer",
		 *     "refresh_token": "68d85c17-e817-4582-95dd-169dce4a3264",
		 *     "expires_in": 7199,
		 *     "scope": "read write"
		 * }
		 */
		if (object instanceof DefaultOAuth2AccessToken) {
			DefaultOAuth2AccessToken accessToken = (DefaultOAuth2AccessToken) object;
			writeKeyValue("access_token", accessToken.getValue());
			writeKeyValue("token_type", accessToken.getTokenType());
			writeKeyValue("refresh_token", accessToken.getRefreshToken().getValue());
			writeKeyValue("expires_in", accessToken.getExpiresIn());
			writeKeyValue("scope", String.join(" ", accessToken.getScope()));
		}
	}
}
