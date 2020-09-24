package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.RelationDetail;

import java.util.List;
import java.util.Map;

public interface IRelationDetailService {
	/**
	 * 根据一个ID得到RelationDetail
	 * 
	 * @param id
	 * @return
	 */
	RelationDetail getObjById(Long id);

	/**
	 * 根据任意字段得到RelationMain
	 *
	 * @param fields
	 * @return
	 */
	RelationDetail getObjByProperty(Object... fields);

	/**
	 * 保存一个RelationDetail，如果保存成功返回true，否则返回false
	 *
	 * @param instance
	 * @return 是否保存成功
	 */
	RelationDetail save(RelationDetail instance);

	/**
	 * 更新一个RelationDetail
	 *
	 * @param instance
	 *            需要更新的RelationDetail
	 */
	RelationDetail update(RelationDetail instance);

	/**
	 * 删除一个RelationDetail
	 * 
	 * @param instance
	 * @return
	 */
	void delete(RelationDetail instance);
	
	/**
	 * 批量删除RelationDetail
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 通过一个查询对象得到RelationDetail
	 * 
	 * @param qo
	 * @return
	 */
	IPageList list(IQueryObject qo);

	/**
	 *
	 * @param qo
	 * @return
	 */
	List<RelationDetail> find(IQueryObject qo);

	/**
	 *
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<RelationDetail> query(String query, Map params, int begin, int max);

}
