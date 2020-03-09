package eon.hg.fap.db.service;

import eon.hg.fap.core.domain.LogType;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;

import java.util.List;
import java.util.Map;

public interface ILogTypeService {
	/**
	 * 保存一个LogType，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(LogType instance);
	
	/**
	 * 根据一个ID得到LogType
	 * 
	 * @param id
	 * @return
	 */
	LogType getObjById(Long id);
	
	/**
	 * 删除一个LogType
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除LogType
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Long> ids);
	
	/**
	 * 通过一个查询对象得到LogType
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个LogType
	 * 
	 * @param instance
	 *            需要更新的LogType
	 */
	boolean update(LogType instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<LogType> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	LogType getObjByProperty(String construct, String propertyName, Object value);

}
