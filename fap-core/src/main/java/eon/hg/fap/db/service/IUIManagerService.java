package eon.hg.fap.db.service;

import eon.hg.fap.core.cache.CacheOperator;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.*;

import java.util.List;
import java.util.Map;

public interface IUIManagerService extends CacheOperator{
	/**
	 * 保存一个UIManager，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	UIManager save(UIManager instance);

	/**
	 * 根据一个ID得到UIManager
	 * 
	 * @param id
	 * @return
	 */
	UIManager getObjById(Long id);
	
	/**
	 * 删除一个UIManager
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 通过一个查询对象得到UIManager
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个UIManager
	 * 
	 * @param instance
	 *            需要更新的UIManager
	 */
	UIManager update(UIManager instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<UIManager> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param properties
	 * @return
	 */
	List<UIManager> find(IQueryObject properties);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	UIManager getObjByProperty(String construct, String propertyName, Object value);

	/**
	 *
	 * @param properties
	 * @return
	 */
	List<UIDetail> findDetail(IQueryObject properties);

	IPageList listConf(QueryObject qoconf);

	List<UIConfDetail> findConfDetail(QueryObject qoconfdetail);

	List<UIConfMain> findConfMain(QueryObject qoconfmain);

    List<UIConf> findConf(QueryObject qoconf);
}
