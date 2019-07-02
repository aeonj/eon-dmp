package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.Role;

import java.util.List;
import java.util.Map;

public interface IRoleService {
	/**
	 * 保存一个Role，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	Role save(Role instance);
	
	/**
	 * 根据一个ID得到Role
	 * 
	 * @param id
	 * @return
	 */
	Role getObjById(Long id);
	
	/**
	 * 删除一个Role
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除Role
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 批量删除Role
	 * 
	 * @param mulIds
	 * @return
	 */
	void batchDelete(String mulIds);
	
	/**
	 * 通过一个查询对象得到Role
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Role
	 * 
	 * @param instance
	 *            需要更新的Role
	 */
	Role update(Role instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Role> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	Role getObjByProperty(String construct, String propertyName, Object value);

    void deleteAllChilds(List<Long> idlist);
}
