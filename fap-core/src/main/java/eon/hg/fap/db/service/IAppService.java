package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.App;

import java.util.List;
import java.util.Map;

public interface IAppService {
	/**
	 * 保存一个App，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(App instance);
	
	/**
	 * 根据一个ID得到App
	 * 
	 * @param id
	 * @return
	 */
	App getObjById(Long id);
	
	/**
	 * 删除一个App
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除App
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Long> ids);
	
	/**
	 * 通过一个查询对象得到App
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个App
	 * 
	 * @param instance
	 *            需要更新的App
	 */
	boolean update(App instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<App> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	App getObjByProperty(String construct, String propertyName, Object value);

}
