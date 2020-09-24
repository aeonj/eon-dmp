package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.PartGroup;

import java.util.List;
import java.util.Map;

public interface IPartGroupService {
	/**
	 * 根据一个ID得到PartGroup
	 *
	 * @param id
	 * @return
	 */
	PartGroup getObjById(Long id);

	/**
	 * 根据任意字段得到PartGroup
	 *
	 * @param fields
	 * @return
	 */
	PartGroup getObjByProperty(Object... fields);

	/**
	 * 保存一个PartGroup，如果保存成功返回true，否则返回false
	 *
	 * @param instance
	 * @return 是否保存成功
	 */
	PartGroup save(PartGroup instance);

	/**
	 * 更新一个PartGroup
	 *
	 * @param instance
	 *            需要更新的PartGroup
	 */
	PartGroup update(PartGroup instance);

	/**
	 * 删除一个PartGroup
	 *
	 * @param id
	 * @return
	 */
	void delete(Long id);

	/**
	 * 批量删除PartGroup
	 *
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);

	/**
	 * 通过一个查询对象得到PartGroup分页列表
	 *
	 * @param qo
	 * @return
	 */
	IPageList list(IQueryObject qo);

	/**
	 * 通过一个查询对象得到PartGroup完整列表
	 *
	 * @param qo
	 * @return
	 */
	List<PartGroup> find(IQueryObject qo);

	/**
	 *
	 * @param query 完整的HQL语句，使用命名参数
	 * @param params 查询条件中的参数的值。使用Map
	 * @param begin 开始查询的位置
	 * @param max 需要查询对象的个数
	 * @return
	 */
	List<PartGroup> query(String query, Map params, int begin, int max);

}
