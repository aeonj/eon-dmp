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
	
	public boolean save(SysLog sysLog) {
		/**
		 * init other field here
		 */
		try {
			this.sysLogDao.save(sysLog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public SysLog getObjById(Long id) {
		SysLog sysLog = this.sysLogDao.get(id);
		if (sysLog != null) {
			return sysLog;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.sysLogDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Long> sysLogIds) {
		// TODO Auto-generated method stub
		for (Serializable id : sysLogIds) {
			delete((Long) id);
		}
		return true;
	}
	
	@Transactional(readOnly = true, propagation= Propagation.SUPPORTS)
	public IPageList list(IQueryObject properties) {
		return sysLogDao.list(properties);
	}

	public boolean update(SysLog sysLog) {
		try {
			this.sysLogDao.update( sysLog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
