package eon.hg.fap.db.service;

import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.mapper.Ccid;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface ICcidService {
	/**
	 * 保存一个Ccid，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	Ccid save(Ccid instance);

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	abstract Ccid getObjById(Class<Ccid> clz, Long id);
	
	/**
	 * 删除一个Ccid
	 * 
	 * @param id
	 * @return
	 */
	void delete(Class clz, Long id);
	
	/**
	 * 批量删除Ccid
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(Class clz, List<Long> ids);
	
	/**
	 * 更新一个Ccid
	 *
	 * @param instance
	 *            需要更新的Ccid
	 */
	Ccid update(Ccid instance);

	/**
	 *
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<IdEntity> query(String query, Map params, int begin, int max);

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Cacheable(key = "#hash_value")
	Ccid getObjByHash(Class<Ccid> clz, String hash_value);

	/**
	 *
	 * @param clz
	 * @param fields
	 * @return
	 */
	Ccid getObjByProperty(Class<Ccid> clz, Object... fields);

	Ccid getCcid(Ccid record);
}
