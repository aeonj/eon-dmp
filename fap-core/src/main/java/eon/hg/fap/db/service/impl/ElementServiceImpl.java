/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月8日
 * @author cxj
 */

package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.ElementDao;
import eon.hg.fap.db.model.primary.Element;
import eon.hg.fap.db.service.IElementService;
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
@CacheConfig(cacheNames = Globals.DEFAULT_TABLE_SUFFIX+"element")
public class ElementServiceImpl implements IElementService {
	@Resource
	private ElementDao elementDao;

	@CachePut(key = "'ele_code-'+#element.ele_code")
	@Override
	public Element save(Element element) {
		return this.elementDao.save(element);
	}

	@Override
	public Element getObjById(Long id) {
		return this.elementDao.get(id);
	}

	@CacheEvict(allEntries = true)
	@Override
	public void delete(Long id) {
		this.elementDao.remove(id);
	}

	@CacheEvict(allEntries = true)
	@Override
	public void batchDelete(List<Long> ids) {
		for (Long id : ids) {
			this.elementDao.remove(id);
		}
	}

	@CacheEvict(allEntries = true)
	@Override
	public void batchDelete(String mulIds) {
		String[] ids = mulIds.split(",");
		for (String id : ids) {
			delete(Long.parseLong(id));
		}
	}

	@Override
	public IPageList list(IQueryObject properties) {
		return elementDao.list(properties);
	}

	@CacheEvict(allEntries = true)
	@Override
	public Element update(Element element) {
		return this.elementDao.update(element);
	}

	@Override
	public List<Element> query(String query, Map params, int begin, int max) {
		return this.elementDao.query(query, params, begin, max);
	}
	
	public List<Element> find(IQueryObject properties) {
		return this.elementDao.find(properties);
	}


	@Cacheable(key = "'ele_code-'+#value", condition = "#propertyName eq 'ele_code'")
	@Override
	public Element getObjByProperty(String construct, String propertyName,
			Object value) {
		return this.elementDao.getOne(propertyName, value);
	}

}
