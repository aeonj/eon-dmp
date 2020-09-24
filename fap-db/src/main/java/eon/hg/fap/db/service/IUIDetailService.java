package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.UIDetail;

import java.util.List;
import java.util.Map;

public interface IUIDetailService {
	/**
	 * 保存一个UIDetail，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(UIDetail instance);
	
	/**
	 * 根据一个ID得到UIDetail
	 * 
	 * @param id
	 * @return
	 */
	UIDetail getObjById(Long id);
	
	/**
	 * 删除一个UIDetail
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除UIDetail
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Long> ids);
	
	/**
	 * 批量删除UIDetail
	 * 
	 * @param mulIds
	 * @return
	 */
	boolean batchDelete(String mulIds);
	
	/**
	 * 通过一个查询对象得到UIDetail
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个UIDetail
	 * 
	 * @param id
	 *            需要更新的UIDetail的id
	 * @param dir
	 *            需要更新的UIDetail
	 */
	boolean update(UIDetail instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<UIDetail> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param qo
	 * @return
	 */
	List<UIDetail> find(IQueryObject properties);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	UIDetail getObjByProperty(String construct, String propertyName, Object value);

}
