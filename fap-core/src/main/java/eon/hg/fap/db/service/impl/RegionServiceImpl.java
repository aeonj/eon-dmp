package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.RegionDao;
import eon.hg.fap.db.model.primary.Region;
import eon.hg.fap.db.service.IRegionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RegionServiceImpl implements IRegionService {
	@Resource
	private RegionDao regionDao;
	
	public boolean save(Region region) {
		/**
		 * init other field here
		 */
		try {
			this.regionDao.save(region);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Region getObjById(Long id) {
		Region region = this.regionDao.get(id);
		if (region != null) {
			return region;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.regionDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Long> regionIds) {
		// TODO Auto-generated method stub
		for (Serializable id : regionIds) {
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
		return regionDao.list(properties);
	}
	
	public List<Region> find(IQueryObject properties) {
		if (properties == null) {
			return null;
		}
		String query = properties.getQuery();
		String construct = properties.getConstruct();
		Map params = properties.getParameters();
		return this.regionDao.find(construct, query, params, -1, -1);
	}
	
	public boolean update(Region region) {
		try {
			this.regionDao.update( region);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<Region> query(String query, Map params, int begin, int max){
		return this.regionDao.query(query, params, begin, max);
		
	}
	
	@Override
	public Region getObjByProperty(String construct, String propertyName,
			Object value) {
		return this.regionDao.getBy(construct, propertyName, value);
	}
	
}
