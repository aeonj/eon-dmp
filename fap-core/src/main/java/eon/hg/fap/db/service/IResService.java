package eon.hg.fap.db.service;

import eon.hg.fap.db.model.primary.Res;

import java.util.List;
import java.util.Map;

public interface IResService {
	/**
	 * 保存一个Res，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	Res save(Res instance);
	
	/**
	 * 根据一个ID得到Res
	 * 
	 * @param id
	 * @return
	 */
	Res getObjById(Long id);
	
	/**
	 * 删除一个Res
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除Res
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 更新一个Res
	 * 
	 * @param instance
	 *            需要更新的Res
	 */
	Res update(Res instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Res> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	Res getObjByProperty(String construct, String propertyName, Object value);

}
