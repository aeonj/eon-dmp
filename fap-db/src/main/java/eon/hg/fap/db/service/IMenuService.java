package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IMenuService {
	/**
	 * 保存一个Menu，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	Menu save(Menu instance);
	
	/**
	 * 根据一个ID得到Menu
	 * 
	 * @param id
	 * @return
	 */
	Menu getObjById(Long id);
	
	/**
	 * 删除一个Menu
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除Menu
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 通过一个查询对象得到Menu
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);

	Page<Menu> list(Pageable pageable);

	/**
	 * 通过查询对象获取未分页的MengGroup列表
	 * @param qo
	 * @return
	 */
	List<Menu> find(IQueryObject qo);
	
	/**
	 * 更新一个Menu
	 * 
	 * @param instance
	 *            需要更新的Menu
	 */
	Menu update(Menu instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Menu> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	Menu getObjByProperty(String construct, String propertyName, Object value);

	void deleteAllChilds(List<Long> idlist);
}
