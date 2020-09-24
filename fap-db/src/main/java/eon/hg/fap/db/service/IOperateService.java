package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.Operate;

import java.util.List;
import java.util.Map;

public interface IOperateService {
	/**
	 * 保存一个Operate，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	Operate save(Operate instance);
	
	/**
	 * 根据一个ID得到Operate
	 * 
	 * @param id
	 * @return
	 */
	Operate getObjById(Long id);
	
	/**
	 * 删除一个Operate
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除Operate
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 批量删除Operate
	 * 
	 * @param mulIds
	 * @return
	 */
	void batchDelete(String mulIds);
	
	/**
	 * 通过一个查询对象得到Operate
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Operate
	 * 
	 * @param instance
	 *            需要更新的Operate
	 */
	Operate update(Operate instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Operate> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param properties
	 * @return
	 */
	List<Operate> find(IQueryObject properties);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	Operate getObjByProperty(String construct, String propertyName, Object value);

    void deleteAllChilds(List<Long> idlist);
}
