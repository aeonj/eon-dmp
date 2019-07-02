package eon.hg.fap.db.service.impl;

import eon.hg.fap.db.dao.primary.ResDao;
import eon.hg.fap.db.model.primary.Res;
import eon.hg.fap.db.service.IResService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ResServiceImpl implements IResService {
	@Resource
	private ResDao resDao;
	
	public boolean save(Res res) {
		/**
		 * init other field here
		 */
		try {
			this.resDao.save(res);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Res getObjById(Long id) {
		Res res = this.resDao.get(id);
		if (res != null) {
			return res;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.resDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Long> resIds) {
		for (Serializable id : resIds) {
			delete((Long) id);
		}
		return true;
	}
	
	public boolean update(Res res) {
		try {
			this.resDao.update( res);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Res> query(String query, Map params, int begin, int max){
		return this.resDao.query(query, params, begin, max);
		
	}
	
	@Override
	public Res getObjByProperty(String construct, String propertyName,
			Object value) {
		return this.resDao.getBy(construct, propertyName, value);
	}
	
}
