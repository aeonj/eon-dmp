package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.MenuGroup;

import java.util.List;
import java.util.Map;

public interface IMenuGroupService {
	/**
	 * 保存一个MenuGroup，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	MenuGroup save(MenuGroup instance);
	
	/**
	 * 根据一个ID得到MenuGroup
	 * 
	 * @param id
	 * @return
	 */
	MenuGroup getObjById(Long id);
	
	/**
	 * 删除一个MenuGroup
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除MenuGroup
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 通过一个查询对象得到MenuGroup
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);

	/**
	 * 通过查询对象获取未分页的MengGroup列表
	 * @param qo
	 * @return
	 */
	List<MenuGroup> find(IQueryObject qo);
	
	/**
	 * 更新一个MenuGroup
	 * 
	 * @param instance
	 *            需要更新的MenuGroup
	 */
	MenuGroup update(MenuGroup instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<MenuGroup> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	MenuGroup getObjByProperty(String construct, String propertyName, Object value);

	void saveDirtyData(String insertdata, String updatedata, String removedata);

}
