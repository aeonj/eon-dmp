package eon.hg.fap.db.service;

import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.OrgType;

import java.util.List;
import java.util.Map;

public interface IOrgTypeService {
	/**
	 * 保存一个OrgType，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	OrgType save(OrgType instance);
	
	/**
	 * 根据一个ID得到OrgType
	 * 
	 * @param id
	 * @return
	 */
	OrgType getObjById(Long id);
	
	/**
	 * 删除一个OrgType
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除OrgType
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 批量删除OrgType
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(String ids);
	
	/**
	 * 通过一个查询对象得到OrgType
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个OrgType
	 * 
	 * @param instance
	 *            需要更新的OrgType
	 */
	OrgType update(OrgType instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<OrgType> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	OrgType getObjByProperty(String construct, String propertyName, Object value);

    List<OrgType> find(QueryObject qo);
}
