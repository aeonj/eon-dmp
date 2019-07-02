package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.OrgTypeDao;
import eon.hg.fap.db.model.primary.OrgType;
import eon.hg.fap.db.service.IOrgTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrgTypeServiceImpl implements IOrgTypeService {
	@Resource
	private OrgTypeDao orgTypeDao;
	
	public OrgType save(OrgType orgType) {
		return this.orgTypeDao.save(orgType);
	}
	
	public OrgType getObjById(Long id) {
		return this.orgTypeDao.get(id);
	}
	
	public void delete(Long id) {
		this.orgTypeDao.remove(id);
	}
	
	public void batchDelete(List<Long> orgTypeIds) {
		for (Long id : orgTypeIds) {
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
		return orgTypeDao.list(properties);
	}
	
	public OrgType update(OrgType orgType) {
		return this.orgTypeDao.update( orgType);
	}

	public List<OrgType> query(String query, Map params, int begin, int max){
		return this.orgTypeDao.query(query, params, begin, max);
		
	}
	
	@Override
	public OrgType getObjByProperty(String construct, String propertyName,
			Object value) {
		return this.orgTypeDao.getBy(construct, propertyName, value);
	}

    @Override
    public List<OrgType> find(QueryObject qo) {
        return orgTypeDao.find(qo);
    }

}
