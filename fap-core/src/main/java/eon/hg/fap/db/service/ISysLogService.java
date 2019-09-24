package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.SysLog;

import java.util.List;
import java.util.Map;

public interface ISysLogService {
	/**
	 * 保存一个SysLog，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	SysLog save(SysLog instance);
	
	/**
	 * 根据一个ID得到SysLog
	 * 
	 * @param id
	 * @return
	 */
	SysLog getObjById(Long id);
	
	/**
	 * 删除一个SysLog
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除SysLog
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 通过一个查询对象得到SysLog
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个SysLog
	 * 
	 * @param instance
	 *            需要更新的SysLog
	 */
	SysLog update(SysLog instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<SysLog> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	SysLog getObjByProperty(String construct, String propertyName, Object value);

}
