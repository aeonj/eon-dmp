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
	
	public SystemTip save(SystemTip systemTip) {
		return this.systemTipDao.save(systemTip);
	}
	
	public SystemTip getObjById(Long id) {
		return this.systemTipDao.get(id);
	}
	
	public void delete(Long id) {
		this.systemTipDao.remove(id);
	}
	
	public void batchDelete(List<Long> systemTipIds) {
		for (Serializable id : systemTipIds) {
			delete((Long) id);
		}
	}
	
	public IPageList list(IQueryObject properties) {
		return systemTipDao.list(properties);
	}
	
	public SystemTip update(SystemTip systemTip) {
		return this.systemTipDao.update( systemTip);
	}

	public List<SystemTip> query(String query, Map params, int begin, int max){
		return this.systemTipDao.query(query, params, begin, max);
		
	}
}
