package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.dao.primary.UserConfigDao;
import eon.hg.fap.db.dao.primary.UserDao;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.model.primary.UserConfig;
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
public class UserConfigServiceImpl implements IUserConfigService {
	@Resource
	private UserConfigDao userConfigDao;
	@Resource
	private UserDao userDAO;
	
	public boolean save(UserConfig userConfig) {
		/**
		 * init other field here
		 */
		try {
			this.userConfigDao.save(userConfig);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public UserConfig getObjById(Long id) {
		UserConfig userConfig = this.userConfigDao.get(id);
		if (userConfig != null) {
			return userConfig;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.userConfigDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Long> userConfigIds) {
		// TODO Auto-generated method stub
		for (Serializable id : userConfigIds) {
			delete((Long) id);
		}
		return true;
	}
	
	public IPageList list(IQueryObject properties) {
		return userConfigDao.list(properties);
	}
	
	public boolean update(UserConfig userConfig) {
		try {
			this.userConfigDao.update( userConfig);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
		User u = SecurityUserHolder.getCurrentUser();
		UserConfig config = null;
		if (u != null) {
			User user = this.userDAO.get(u.getId());
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
				}
			}
		} else {
			config = new UserConfig();
			config.setTheme(Globals.DEFAULT_THEME);
			config.setLayout(AeonConstants.APP_LAYOUT_CLASSIC);
		}
		return config;
	}
	
}
