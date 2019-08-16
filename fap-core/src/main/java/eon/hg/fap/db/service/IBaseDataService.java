package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.mapper.BaseData;

import java.util.List;
import java.util.Map;

public interface IBaseDataService {
	/**
	 * 保存一个BaseData，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	BaseData save(BaseData instance);
	
	/**
	 * 根据一个ID得到BaseData
	 * 
	 * @param id
	 * @return
	 */
	BaseData getObjById(Class clz, Long id);
	
	/**
	 * 删除一个BaseData
	 * 
	 * @param id
	 * @return
	 */
	void delete(Class clz, Long id);
	
	/**
	 * 批量删除BaseData
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(Class clz, List<Long> ids);
	
	/**
	 * 通过一个查询对象得到BaseData
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(Class clz, IQueryObject properties);

	/**
	 * 更新一个BaseData
	 *
	 * @param instance
	 *            需要更新的BaseData
	 */
	BaseData update(BaseData instance);

	/**
	 *
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<BaseData> query(String query, Map params, int begin, int max);

	/**
	 *
	 * @param clz
	 * @param properties
	 * @return
	 */
	public List<BaseData> find(Class clz, IQueryObject properties);

	/**
	 *
	 * @param propertyName
	 * @param value
	 * @return
	 */
	BaseData getObjByProperty(Class clz, String construct, String propertyName, Object value);
	
	List<BaseData> findBySql(String sql);
	List<BaseData> findBySql(String sql, Object[] obj);

}
