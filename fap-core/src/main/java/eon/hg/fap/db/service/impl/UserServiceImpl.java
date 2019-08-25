package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.UserDao;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Resource
	private UserDao userDao;

	public User save(User user) {
		return this.userDao.save(user);
	}

	public User getObjById(Long id) {
		return this.userDao.get(id);
	}

	public void delete(Long id) {
		this.userDao.remove(id);
	}
	
	public void batchDelete(List<Long> userIds) {
		for (Long id : userIds) {
			delete(id);
		}
	}
	
	public IPageList list(IQueryObject properties) {
		return userDao.list(properties);
	}

	public User update(User user) {
		return this.userDao.update( user);
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
