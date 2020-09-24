package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.Area;

import java.util.List;
import java.util.Map;

public interface IAreaService {
	/**
	 * 保存一个Area，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(Area instance);
	
	/**
	 * 根据一个ID得到Area
	 * 
	 * @param id
	 * @return
	 */
	Area getObjById(Long id);
	
	/**
	 * 删除一个Area
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除Area
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Long> ids);
	
	/**
	 * 通过一个查询对象得到Area
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Area
	 * 
	 * @param instance
	 *            需要更新的Area
	 */
	boolean update(Area instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Area> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param properties
	 * @return
	 */
	List<Area> find(IQueryObject properties);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	Area getObjByProperty(String construct, String propertyName, Object value);

}
