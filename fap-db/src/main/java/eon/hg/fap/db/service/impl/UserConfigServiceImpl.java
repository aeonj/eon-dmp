package eon.hg.fap.db.service.impl;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.cache.AbstractCacheOperator;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.dao.primary.UserConfigDao;
import eon.hg.fap.db.dao.primary.UserDao;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.model.primary.UserConfig;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.db.service.IUserConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserConfigServiceImpl extends AbstractCacheOperator implements IUserConfigService {
	@Resource
	private UserConfigDao userConfigDao;
	@Resource
	private UserDao userDAO;
	
	public UserConfig save(UserConfig userConfig) {
		return this.userConfigDao.save(userConfig);
	}
	
	public UserConfig getObjById(Long id) {
		return this.userConfigDao.get(id);
	}
	
	public void delete(Long id) {
		this.userConfigDao.remove(id);
	}
	
	public void batchDelete(List<Long> userConfigIds) {
		for (Serializable id : userConfigIds) {
			delete((Long) id);
		}
	}
	
	public IPageList list(IQueryObject properties) {
		return userConfigDao.list(properties);
	}
	
	public UserConfig update(UserConfig userConfig) {
		return this.userConfigDao.update( userConfig);
	}

	public List<UserConfig> query(String query, Map params, int begin, int max){
		return this.userConfigDao.query(query, params, begin, max);
		
	}
	
	@Override
	public UserConfig getObjByProperty(String construct, String propertyName,
			Object value) {
		return this.userConfigDao.getBy(construct, propertyName, value);
	}

	@Override
	public UserConfig getUserConfig() {
		OnlineUser u = SecurityUserHolder.getOnlineUser();
		UserConfig config = null;
		if (u != null) {
			try {
				config = getCache(u.getUserid());
			} catch (Exception e) {
				config = getObject(u.getUserid());
			}

		} else {
			config = new UserConfig();
			config.setTheme(Globals.DEFAULT_THEME);
			config.setLayout(AeonConstants.APP_LAYOUT_CLASSIC);
		}
		return config;
	}

	@Override
	public String getCacheId(Object... params) {
		return "user_config";
	}

	@Override
	public String getKey(Object... params) {
		return CommUtil.null2String(params[0]);
	}

	@Override
	public UserConfig getObject(Object... params) {
		UserConfig config;
		User user = this.userDAO.get(CommUtil.null2Long(params[0]));
		if (user != null) {
			config = user.getConfig();
			if (config==null) {
				config = new UserConfig();
				config.setUser_id(user.getId());
				config.setAddTime(new Date());
				config.setTheme(Globals.DEFAULT_THEME);
				config.setLayout(AeonConstants.APP_LAYOUT_CLASSIC);
				this.save(config);
				user.setConfig(config);
				this.userDAO.update(user);
			}
		} else {
			config = new UserConfig();
			config.setTheme(Globals.DEFAULT_THEME);
			config.setLayout(AeonConstants.APP_LAYOUT_CLASSIC);
		}
		return config;
	}
}
