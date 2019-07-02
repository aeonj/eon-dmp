package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.UserDao;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Resource
	private UserDao userDao;
	
	public boolean save(User user) {
		/**
		 * init other field here
		 */
		try {
			this.userDao.save(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public User getObjById(Long id) {
		User user = this.userDao.get(id);
		if (user != null) {
			return user;
		}
		return null;
	}

	public boolean delete(Long id) {
		try {
			this.userDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Long> userIds) {
		// TODO Auto-generated method stub
		for (Serializable id : userIds) {
			delete((Long) id);
		}
		return true;
	}
	
	public IPageList list(IQueryObject properties) {
		return userDao.list(properties);
	}
	
	public boolean update(User user) {
		try {
			this.userDao.update( user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<User> query(String query, Map params, int begin, int max){
		return this.userDao.query(query, params, begin, max);
		
	}
	
	@Override
	public User getObjByProperty(String construct, String propertyName,
			Object value) {
		return this.userDao.getBy(construct, propertyName, value);
	}

}
