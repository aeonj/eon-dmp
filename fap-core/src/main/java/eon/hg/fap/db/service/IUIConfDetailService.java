package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.UIConfDetail;

import java.util.List;
import java.util.Map;

public interface IUIConfDetailService {
	/**
	 * 保存一个UIConfDetail，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(UIConfDetail instance);
	
	/**
	 * 根据一个ID得到UIConfDetail
	 * 
	 * @param id
	 * @return
	 */
	UIConfDetail getObjById(Long id);
	
	/**
	 * 删除一个UIConfDetail
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除UIConfDetail
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Long> ids);
	
	/**
	 * 批量删除UIConfDetail
	 * 
	 * @param mulIds
	 * @return
	 */
	boolean batchDelete(String mulIds);
	
	/**
	 * 通过一个查询对象得到UIConfDetail
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个UIConfDetail
	 * 
	 * @param id
	 *            需要更新的UIConfDetail的id
	 * @param dir
	 *            需要更新的UIConfDetail
	 */
	boolean update(UIConfDetail instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<UIConfDetail> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param qo
	 * @return
	 */
	List<UIConfDetail> find(IQueryObject properties);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	UIConfDetail getObjByProperty(String construct, String propertyName, Object value);

}
