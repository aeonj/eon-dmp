package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.RegionOperate;

import java.util.List;
import java.util.Map;

public interface IRegionOperateService {
	/**
	 * 保存一个RegionOperate，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(RegionOperate instance);
	
	/**
	 * 根据一个ID得到RegionOperate
	 * 
	 * @param id
	 * @return
	 */
	RegionOperate getObjById(Long id);
	
	/**
	 * 删除一个RegionOperate
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除RegionOperate
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Long> ids);
	
	/**
	 * 批量删除RegionOperate
	 * 
	 * @param mulIds
	 * @return
	 */
	boolean batchDelete(String mulIds);
	
	/**
	 * 通过一个查询对象得到RegionOperate
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个RegionOperate
	 * 
	 * @param id
	 *            需要更新的RegionOperate的id
	 * @param dir
	 *            需要更新的RegionOperate
	 */
	boolean update(RegionOperate instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<RegionOperate> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param qo
	 * @return
	 */
	List<RegionOperate> find(IQueryObject properties);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	RegionOperate getObjByProperty(String construct, String propertyName, Object value);

}
