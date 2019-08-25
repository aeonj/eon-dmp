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

	public Res save(Res res) {
		return this.resDao.save(res);
	}

	public Res getObjById(Long id) {
		Res res = this.resDao.get(id);
		if (res != null) {
			return res;
		}
		return null;
	}

	public void delete(Long id) {
		this.resDao.remove(id);
	}

	public void batchDelete(List<Long> resIds) {
		for (Serializable id : resIds) {
			delete((Long) id);
		}
	}

	public Res update(Res res) {
		return this.resDao.update( res);
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
