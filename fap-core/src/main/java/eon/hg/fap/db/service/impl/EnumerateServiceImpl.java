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
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EnumerateServiceImpl implements IEnumerateService {
	@Resource
	private EnumerateDao enumerateDao;
	
	public boolean save(Enumerate enumerate) {
		/**
		 * init other field here
		 */
		try {
			this.enumerateDao.save(enumerate);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Enumerate getObjById(Long id) {
		Enumerate enumerate = this.enumerateDao.get(id);
		if (enumerate != null) {
			return enumerate;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.enumerateDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Long> enumerateIds) {
		// TODO Auto-generated method stub
		for (Serializable id : enumerateIds) {
			delete((Long) id);
		}
		return true;
	}
	
	@Override
	public boolean batchDelete(String mulIds) {
		String[] ids = mulIds.split(",");
		for (String id : ids) {
			delete(Long.parseLong(id));
		}
		return true;
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
	
	public boolean update(Enumerate enumerate) {
		try {
			this.enumerateDao.update( enumerate);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
