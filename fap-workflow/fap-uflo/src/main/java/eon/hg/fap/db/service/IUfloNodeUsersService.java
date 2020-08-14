package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.UfloNodeUsers;

import java.util.List;
import java.util.Map;

public interface IUfloNodeUsersService {
	/**
	 * 保存一个UfloNodeUsers，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	UfloNodeUsers save(UfloNodeUsers instance);
	
	/**
	 * 根据一个ID得到UfloNodeUsers
	 * 
	 * @param id
	 * @return
	 */
	UfloNodeUsers getObjById(Long id);
	
	/**
	 * 删除一个UfloNodeUsers
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除UfloNodeUsers
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 批量删除UfloNodeUsers
	 * 
	 * @param mulIds
	 * @return
	 */
	void batchDelete(String mulIds);
	
	/**
	 * 通过一个查询对象得到UfloNodeUsers
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个UfloNodeUsers
	 * 
	 * @param instance
	 *            需要更新的UfloNodeUsers
	 */
	UfloNodeUsers update(UfloNodeUsers instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<UfloNodeUsers> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param qo
	 * @return
	 */
	List<UfloNodeUsers> find(IQueryObject qo);
	
	/**
	 * 
	 * @param fields
	 * @return
	 */
	UfloNodeUsers getObjByProperty(Object... fields);

}
