package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.OperateDao;
import eon.hg.fap.db.model.primary.Operate;
import eon.hg.fap.db.service.IOperateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OperateServiceImpl implements IOperateService {
	@Resource
	private OperateDao operateDao;
	
	public Operate save(Operate operate) {
		return this.operateDao.save(operate);
	}
	
	public Operate getObjById(Long id) {
		Operate operate = this.operateDao.get(id);
		if (operate != null) {
			return operate;
		}
		return null;
	}
	
	public void delete(Long id) {
		this.operateDao.remove(id);
	}
	
	public void batchDelete(List<Long> operateIds) {
		for (Long id : operateIds) {
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
		return operateDao.list(properties);
	}
	
	public List<Operate> find(IQueryObject properties) {
		return this.operateDao.find(properties);
	}
	
	public Operate update(Operate operate) {
		return this.operateDao.update(operate);
	}

	public List<Operate> query(String query, Map params, int begin, int max){
		return this.operateDao.query(query, params, begin, max);
		
	}
	
	@Override
	public Operate getObjByProperty(String construct, String propertyName,
			Object value) {
		return this.operateDao.getBy(construct, propertyName, value);
	}

	@Override
	public void deleteAllChilds(List<Long> idlist) {
		List<Operate> operateList = this.operateDao.findAllById(idlist);
		this.operateDao.deleteAll(operateList);
	}

}
