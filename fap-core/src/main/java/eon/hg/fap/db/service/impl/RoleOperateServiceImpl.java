package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.RoleOperateDao;
import eon.hg.fap.db.model.primary.RoleOperate;
import eon.hg.fap.db.service.IRoleOperateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleOperateServiceImpl implements IRoleOperateService {
	@Resource
	private RoleOperateDao roleOperateDao;
	
	public RoleOperate save(RoleOperate roleOperate) {
		return this.roleOperateDao.save(roleOperate);
	}
	
	public RoleOperate getObjById(Long id) {
		RoleOperate roleOperate = this.roleOperateDao.get(id);
		if (roleOperate != null) {
			return roleOperate;
		}
		return null;
	}
	
	public void delete(Long id) {
		this.roleOperateDao.remove(id);
	}
	
	public void delete(RoleOperate ro) {
		this.roleOperateDao.remove(ro);
	}

	public void batchDelete(List<Long> roleOperateIds) {
		for (Serializable id : roleOperateIds) {
			delete((Long) id);
		}
	}
	
	@Override
	public void batchDelete(String mulIds) {
		String[] ids = mulIds.split(",");
		for (String id : ids) {
			delete(Long.parseLong(id));
		}
	}

	public IPageList list(IQueryObject properties) {
		return roleOperateDao.list(properties);
	}
	
	public List<RoleOperate> find(IQueryObject properties) {
		if (properties == null) {
			return null;
		}
		String query = properties.getQuery();
		String construct = properties.getConstruct();
		Map params = properties.getParameters();
		return this.roleOperateDao.find(construct, query, params, -1, -1);
	}
	
	public RoleOperate update(RoleOperate roleOperate) {
		return this.roleOperateDao.update( roleOperate);
	}
	public List<RoleOperate> query(String query, Map params, int begin, int max){
		return this.roleOperateDao.query(query, params, begin, max);
		
	}
	
	@Override
	public RoleOperate getObjByProperty(String construct, String propertyName,
			Object value) {
		return this.roleOperateDao.getBy(construct, propertyName, value);
	}
	
}
