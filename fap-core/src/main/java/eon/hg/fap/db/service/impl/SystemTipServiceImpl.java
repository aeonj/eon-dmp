package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.SystemTipDao;
import eon.hg.fap.db.model.primary.SystemTip;
import eon.hg.fap.db.service.ISystemTipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SystemTipServiceImpl implements ISystemTipService {
	@Resource
	private SystemTipDao systemTipDao;
	
	public boolean save(SystemTip systemTip) {
		/**
		 * init other field here
		 */
		try {
			this.systemTipDao.save(systemTip);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public SystemTip getObjById(Long id) {
		SystemTip systemTip = this.systemTipDao.get(id);
		if (systemTip != null) {
			return systemTip;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.systemTipDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Long> systemTipIds) {
		// TODO Auto-generated method stub
		for (Serializable id : systemTipIds) {
			delete((Long) id);
		}
		return true;
	}
	
	public IPageList list(IQueryObject properties) {
		return systemTipDao.list(properties);
	}
	
	public boolean update(SystemTip systemTip) {
		try {
			this.systemTipDao.update( systemTip);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<SystemTip> query(String query, Map params, int begin, int max){
		return this.systemTipDao.query(query, params, begin, max);
		
	}
}
