package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.EnumerateDao;
import eon.hg.fap.db.model.primary.Enumerate;
import eon.hg.fap.db.service.IEnumerateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EnumerateServiceImpl implements IEnumerateService {
	@Resource
	private EnumerateDao enumerateDao;
	
	public Enumerate save(Enumerate enumerate) {
		return this.enumerateDao.save(enumerate);
	}
	
	public Enumerate getObjById(Long id) {
		return this.enumerateDao.get(id);
	}
	
	public void delete(Long id) {
		this.enumerateDao.remove(id);
	}
	
	public void batchDelete(List<Long> enumerateIds) {
		for (Long id : enumerateIds) {
			this.enumerateDao.remove(id);
		}
	}
	
	public IPageList list(IQueryObject properties) {
		return enumerateDao.list(properties);
	}
	
	public List<Enumerate> find(IQueryObject properties) {
		if (properties == null) {
			return null;
		}
		String query = properties.getQuery();
		String construct = properties.getConstruct();
		Map params = properties.getParameters();
		return this.enumerateDao.find(construct, query, params, -1, -1);
	}
	
	public Enumerate update(Enumerate enumerate) {
		return this.enumerateDao.update( enumerate);
	}
	public List<Enumerate> query(String query, Map params, int begin, int max){
		return this.enumerateDao.query(query, params, begin, max);
		
	}
	
	@Override
	public Enumerate getObjByProperty(String construct, String propertyName,
			Object value) {
		return this.enumerateDao.getBy(construct, propertyName, value);
	}
	
	@Override
	public List<Enumerate> getCodeList(String field) {
		QueryObject qo = new QueryObject();
		qo.addQuery("field", "p_field", field, "=");
		qo.setOrderBy("sortno");
		return this.find(qo);
	}
	
}
