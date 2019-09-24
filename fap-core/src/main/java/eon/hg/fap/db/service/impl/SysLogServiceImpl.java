package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.SysLogDao;
import eon.hg.fap.db.model.primary.SysLog;
import eon.hg.fap.db.service.ISysLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysLogServiceImpl implements ISysLogService {
	@Resource
	private SysLogDao sysLogDao;
	
	public SysLog save(SysLog sysLog) {
		return this.sysLogDao.save(sysLog);
	}
	
	public SysLog getObjById(Long id) {
		return this.sysLogDao.get(id);
	}
	
	public void delete(Long id) {
		this.sysLogDao.remove(id);
	}
	
	public void batchDelete(List<Long> sysLogIds) {
		for (Serializable id : sysLogIds) {
			delete((Long) id);
		}
	}
	
	@Transactional(readOnly = true, propagation= Propagation.SUPPORTS)
	public IPageList list(IQueryObject properties) {
		return sysLogDao.list(properties);
	}

	public SysLog update(SysLog sysLog) {
		return this.sysLogDao.update(sysLog);
	}

	public List<SysLog> query(String query, Map params, int begin, int max){
		return this.sysLogDao.query(query, params, begin, max);
		
	}
	
	@Override
	public SysLog getObjByProperty(String construct, String propertyName,
			Object value) {
		return this.sysLogDao.getBy(construct, propertyName, value);
	}
	
}
