/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月8日
 * @author cxj
 */

package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.UfloNodeUsersDao;
import eon.hg.fap.db.model.primary.UfloNodeUsers;
import eon.hg.fap.db.service.IUfloNodeUsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author AEON
 *
 */
@Service
@Transactional
public class UfloNodeUsersServiceImpl implements IUfloNodeUsersService {
	@Resource
	private UfloNodeUsersDao currentTasksDao;

	@Override
	public UfloNodeUsers save(UfloNodeUsers element) {
		return this.currentTasksDao.save(element);
	}

	@Override
	public UfloNodeUsers getObjById(Long id) {
		return this.currentTasksDao.get(id);
	}

	@Override
	public void delete(Long id) {
		this.currentTasksDao.remove(id);
	}

	@Override
	public void batchDelete(List<Long> ids) {
		for (Long id : ids) {
			this.currentTasksDao.remove(id);
		}
	}

	@Override
	public void batchDelete(String mulIds) {
		String[] ids = mulIds.split(",");
		for (String id : ids) {
			delete(Long.parseLong(id));
		}
	}

	@Override
	public IPageList list(IQueryObject properties) {
		return currentTasksDao.list(properties);
	}

	@Override
	public UfloNodeUsers update(UfloNodeUsers element) {
		return this.currentTasksDao.update(element);
	}

	@Override
	public List<UfloNodeUsers> query(String query, Map params, int begin, int max) {
		return this.currentTasksDao.query(query, params, begin, max);
	}
	
	public List<UfloNodeUsers> find(IQueryObject properties) {
		return this.currentTasksDao.find(properties);
	}


	@Override
	public UfloNodeUsers getObjByProperty(Object... fields) {
		return this.currentTasksDao.getOne(fields);
	}

}
