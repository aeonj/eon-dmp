package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.UIConf;

import java.util.List;
import java.util.Map;

public interface IUIConfService {
	/**
	 * 保存一个UIConf，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	UIConf save(UIConf instance);
	
	/**
	 * 根据一个ID得到UIConf
	 * 
	 * @param id
	 * @return
	 */
	UIConf getObjById(Long id);
	
	/**
	 * 删除一个UIConf
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);

	/**
	 * 通过一个查询对象得到UIConf
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个UIConf
	 * 
	 * @param instance
	 *            需要更新的UIConf
	 */
	UIConf update(UIConf instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<UIConf> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param properties
	 * @return
	 */
	List<UIConf> find(IQueryObject properties);
	
	/**
	 * 
	 * @param fields
	 * @return
	 */
	UIConf getObjByProperty(Object... fields);

    void deleteAll();
}
