package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.SystemTip;

import java.util.List;
import java.util.Map;

public interface ISystemTipService {
	/**
	 * 保存一个SystemTip，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	SystemTip save(SystemTip instance);
	
	/**
	 * 根据一个ID得到SystemTip
	 * 
	 * @param id
	 * @return
	 */
	SystemTip getObjById(Long id);
	
	/**
	 * 删除一个SystemTip
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除SystemTip
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 通过一个查询对象得到SystemTip
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个SystemTip
	 * 
	 * @param instance
	 *            需要更新的SystemTip
	 */
	SystemTip update(SystemTip instance);

	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<SystemTip> query(String query, Map params, int begin, int max);
}
