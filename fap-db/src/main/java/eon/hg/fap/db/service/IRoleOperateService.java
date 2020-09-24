package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.RoleOperate;

import java.util.List;
import java.util.Map;

public interface IRoleOperateService {
	/**
	 * 保存一个RoleOperate，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	RoleOperate save(RoleOperate instance);
	
	/**
	 * 根据一个ID得到RoleOperate
	 * 
	 * @param id
	 * @return
	 */
	RoleOperate getObjById(Long id);
	
	/**
	 * 删除一个RoleOperate
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 删除一个RoleOperate
	 *
	 * @param ro
	 * @return
	 */
	void delete(RoleOperate ro);

	/**
	 * 批量删除RoleOperate
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 批量删除RoleOperate
	 * 
	 * @param mulIds
	 * @return
	 */
	void batchDelete(String mulIds);
	
	/**
	 * 通过一个查询对象得到RoleOperate
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个RoleOperate
	 * 
	 * @param instance
	 *            需要更新的RoleOperate
	 */
	RoleOperate update(RoleOperate instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<RoleOperate> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param properties
	 * @return
	 */
	List<RoleOperate> find(IQueryObject properties);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	RoleOperate getObjByProperty(String construct, String propertyName, Object value);

}
