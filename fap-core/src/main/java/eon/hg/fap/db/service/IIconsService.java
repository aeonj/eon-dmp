package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.Icons;

import java.util.List;
import java.util.Map;

public interface IIconsService {
	/**
	 * 保存一个Icons，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	Icons save(Icons instance);
	
	/**
	 * 根据一个ID得到Icons
	 * 
	 * @param id
	 * @return
	 */
	Icons getObjById(Long id);
	
	/**
	 * 删除一个Icons
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除Icons
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 批量删除Icons
	 * 
	 * @param mulIds
	 * @return
	 */
	void batchDelete(String mulIds);
	
	/**
	 * 通过一个查询对象得到Icons
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Icons
	 * 
	 * @param instance
	 *            需要更新的Icons
	 */
	Icons update(Icons instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Icons> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param qo
	 * @return
	 */
	List<Icons> find(IQueryObject qo);
	
	/**
	 * 
	 * @param fields
	 * @return
	 */
	Icons getObjByProperty(Object... fields);

}
