package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.RoleDao;
import eon.hg.fap.db.model.primary.Role;
import eon.hg.fap.db.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
	@Resource
	private RoleDao roleDao;
	
	public Role save(Role role) {
		return this.roleDao.save(role);
	}
	
	public Role getObjById(Long id) {
		Role role = this.roleDao.get(id);
		if (role != null) {
			return role;
		}
		return null;
	}
	
	public void delete(Long id) {
		this.roleDao.remove(id);
	}
	
	public void batchDelete(List<Long> roleIds) {
		for (Long id : roleIds) {
			delete(id);
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
		return roleDao.list(properties);
	}
	
	public Role update(Role role) {
		return this.roleDao.update( role);
	}

	public List<Role> query(String query, Map params, int begin, int max){
		return this.roleDao.query(query, params, begin, max);
		
	}
	
	@Override
	public Role getObjByProperty(String construct, String propertyName,
			Object value) {
		return this.roleDao.getBy(construct, propertyName, value);
	}

    @Override
    public void deleteAllChilds(List<Long> idlist) {
		List<Role> roleList = this.roleDao.findAllById(idlist);
		this.roleDao.deleteAll(roleList);
    }

}
