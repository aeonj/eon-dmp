package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.Element;

import java.util.List;
import java.util.Map;

public interface IElementService {
	/**
	 * 保存一个Element，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	Element save(Element instance);
	
	/**
	 * 根据一个ID得到Element
	 * 
	 * @param id
	 * @return
	 */
	Element getObjById(Long id);
	
	/**
	 * 删除一个Element
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除Element
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 批量删除Element
	 * 
	 * @param mulIds
	 * @return
	 */
	void batchDelete(String mulIds);
	
	/**
	 * 通过一个查询对象得到Element
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Element
	 * 
	 * @param instance
	 *            需要更新的Element
	 */
	Element update(Element instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Element> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param qo
	 * @return
	 */
	List<Element> find(IQueryObject qo);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	Element getObjByProperty(String construct, String propertyName, Object value);

}
