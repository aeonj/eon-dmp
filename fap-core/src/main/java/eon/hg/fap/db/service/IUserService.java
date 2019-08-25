package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
	/**
	 * 保存一个User，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	User save(User instance);
	
	/**
	 * 根据一个ID得到User
	 * 
	 * @param id
	 * @return
	 */
	User getObjById(Long id);
	
	/**
	 * 删除一个User
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除User
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 通过一个查询对象得到User
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个User
	 * 
	 */
	User update(User instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<User> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	User getObjByProperty(String construct, String propertyName, Object value);
	
}
