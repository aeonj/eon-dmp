package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.PartGroup;

import java.util.List;
import java.util.Map;

public interface IPartGroupService {
	/**
	 * 保存一个PartGroup，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(PartGroup instance);
	
	/**
	 * 根据一个ID得到PartGroup
	 * 
	 * @param id
	 * @return
	 */
	PartGroup getObjById(Long id);
	
	/**
	 * 删除一个PartGroup
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除PartGroup
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Long> ids);
	
	/**
	 * 批量删除PartGroup
	 * 
	 * @param mulIds
	 * @return
	 */
	boolean batchDelete(String mulIds);
	
	/**
	 * 通过一个查询对象得到PartGroup
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个PartGroup
	 * 
	 * @param id
	 *            需要更新的PartGroup的id
	 * @param dir
	 *            需要更新的PartGroup
	 */
	boolean update(PartGroup instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<PartGroup> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param qo
	 * @return
	 */
	List<PartGroup> find(IQueryObject properties);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	PartGroup getObjByProperty(String construct, String propertyName, Object value);

}
