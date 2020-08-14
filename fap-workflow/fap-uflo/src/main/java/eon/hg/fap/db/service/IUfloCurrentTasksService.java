package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.UfloCurrentTasks;

import java.util.List;
import java.util.Map;

public interface IUfloCurrentTasksService {
	/**
	 * 保存一个UfloCurrentTasks，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	UfloCurrentTasks save(UfloCurrentTasks instance);
	
	/**
	 * 根据一个ID得到UfloCurrentTasks
	 * 
	 * @param id
	 * @return
	 */
	UfloCurrentTasks getObjById(Long id);
	
	/**
	 * 删除一个UfloCurrentTasks
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除UfloCurrentTasks
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 批量删除UfloCurrentTasks
	 * 
	 * @param mulIds
	 * @return
	 */
	void batchDelete(String mulIds);
	
	/**
	 * 通过一个查询对象得到UfloCurrentTasks
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个UfloCurrentTasks
	 * 
	 * @param instance
	 *            需要更新的UfloCurrentTasks
	 */
	UfloCurrentTasks update(UfloCurrentTasks instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<UfloCurrentTasks> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param qo
	 * @return
	 */
	List<UfloCurrentTasks> find(IQueryObject qo);
	
	/**
	 * 
	 * @param fields
	 * @return
	 */
	UfloCurrentTasks getObjByProperty(Object... fields);

}
