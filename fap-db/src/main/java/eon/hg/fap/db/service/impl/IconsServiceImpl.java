/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月8日
 * @author cxj
 */

package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.IconsDao;
import eon.hg.fap.db.model.primary.Icons;
import eon.hg.fap.db.service.IIconsService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author AEON
 *
 */
@Service
@Transactional
@CacheConfig(cacheNames = Globals.DEFAULT_TABLE_SUFFIX+"Icons")
public class IconsServiceImpl implements IIconsService {
	@Resource
	private IconsDao IconsDao;

	@CachePut(key = "'icons-'+#Icons.icons")
	@Override
	public Icons save(Icons Icons) {
		return this.IconsDao.save(Icons);
	}

	@Override
	public Icons getObjById(Long id) {
		return this.IconsDao.get(id);
	}

	@CacheEvict(allEntries = true)
	@Override
	public void delete(Long id) {
		this.IconsDao.remove(id);
	}

	@CacheEvict(allEntries = true)
	@Override
	public void batchDelete(List<Long> ids) {
		for (Long id : ids) {
			this.IconsDao.remove(id);
		}
	}

	@Override
	public void batchDelete(String mulIds) {
		String[] ids = mulIds.split(",");
		for (String id : ids) {
			delete(Long.parseLong(id));
		}
	}

	@Override
	public IPageList list(IQueryObject properties) {
		return IconsDao.list(properties);
	}

	@CachePut(key = "'icons-'+#Icons.Icons")
	@Override
	public Icons update(Icons Icons) {
		return this.IconsDao.update(Icons);
	}

	@Override
	public List<Icons> query(String query, Map params, int begin, int max) {
		return this.IconsDao.query(query, params, begin, max);
	}
	
	public List<Icons> find(IQueryObject properties) {
		return this.IconsDao.find(properties);
	}


	@Cacheable(key = "#propertyName+'-'+#value")
	@Override
	public Icons getObjByProperty(Object... fields) {
		return this.IconsDao.getOne(fields);
	}

}
