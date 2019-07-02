package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.Enumerate;

import java.util.List;
import java.util.Map;

public interface IEnumerateService {
	/**
	 * 保存一个Enumerate，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(Enumerate instance);
	
	/**
	 * 根据一个ID得到Enumerate
	 * 
	 * @param id
	 * @return
	 */
	Enumerate getObjById(Long id);
	
	/**
	 * 删除一个Enumerate
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除Enumerate
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Long> ids);
	
	/**
	 * 批量删除Enumerate
	 * 
	 * @param mulIds
	 * @return
	 */
	boolean batchDelete(String mulIds);
	
	/**
	 * 通过一个查询对象得到Enumerate
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Enumerate
	 * 
	 * @param instance
	 *            需要更新的Enumerate
	 */
	boolean update(Enumerate instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Enumerate> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param properties
	 * @return
	 */
	List<Enumerate> find(IQueryObject properties);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	Enumerate getObjByProperty(String construct, String propertyName, Object value);

	/**
	 * 获取字典数据
	 * @param field 对照字段
	 * @return
	 */
	public List<Enumerate> getCodeList(String field) ;

}
