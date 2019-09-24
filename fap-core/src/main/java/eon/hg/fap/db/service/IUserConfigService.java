package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.UserConfig;

import java.util.List;
import java.util.Map;

public interface IUserConfigService {
	/**
	 * 保存一个UserConfig，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	UserConfig save(UserConfig instance);
	
	/**
	 * 根据一个ID得到UserConfig
	 * 
	 * @param id
	 * @return
	 */
	UserConfig getObjById(Long id);
	
	/**
	 * 删除一个UserConfig
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除UserConfig
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 通过一个查询对象得到UserConfig
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个UserConfig
	 * 
	 * @param instance
	 *            需要更新的UserConfig
	 */
	UserConfig update(UserConfig instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<UserConfig> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	UserConfig getObjByProperty(String construct, String propertyName, Object value);

	/**
	 * 
	 * @return
	 */
	UserConfig getUserConfig();

}
