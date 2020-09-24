package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.UIConfDao;
import eon.hg.fap.db.model.primary.UIConf;
import eon.hg.fap.db.service.IUIConfService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UIConfServiceImpl implements IUIConfService {
	@Resource
	private UIConfDao uIConfDao;
	
	public UIConf save(UIConf uIConf) {
		return this.uIConfDao.save(uIConf);
	}

	public UIConf update(UIConf uIConf) {
		return this.uIConfDao.update( uIConf);
	}

	public UIConf getObjById(Long id) {
		return this.uIConfDao.get(id);
	}
	
	public void delete(Long id) {
		this.uIConfDao.remove(id);
	}
	
	public IPageList list(IQueryObject properties) {
		return this.uIConfDao.list(properties);
	}
	
	public List<UIConf> find(IQueryObject properties) {
		return this.uIConfDao.find(properties);
	}
	
	public List<UIConf> query(String query, Map params, int begin, int max){
		return this.uIConfDao.query(query, params, begin, max);
		
	}
	
	@Override
	public UIConf getObjByProperty(Object... fields) {
		return this.uIConfDao.getOne(fields);
	}

	@Override
	public void deleteAll() {
		this.uIConfDao.deleteAll();
	}

}
