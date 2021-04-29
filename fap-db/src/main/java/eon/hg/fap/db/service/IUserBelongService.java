package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.UserBelong;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface IUserBelongService {

	/**
	 * 保存用户权限
	 * @param instance
	 * @return
	 */
	UserBelong save(UserBelong instance);

	/**
	 * 根据一个ID得到User
	 * 
	 * @param id
	 * @return
	 */
	UserBelong getObjById(Long id);
	
    /**
     * 删除一个UserBelong
	 * @param id
	 */
	void delete(Long id);

    /**
     * 批量删除UserBelong
	 * @param ids
	 */
	void batchDelete(List<Long> ids);

	/**
	 * 通过一个查询对象得到UserBelong
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
     * 更新一个UserBelong
	 * @param instance
     * @return
     */
	UserBelong update(UserBelong instance);

	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<UserBelong> query(String query, Map params, int begin, int max);

	/**
	 * @param qo
	 * @return
	 */
	public List<UserBelong> find(IQueryObject qo);

	/**
	 * 
	 * @param fields
	 * @return
	 */
	UserBelong getObjByProperty(Object... fields);

	/**
	 * 根据用户删除权限信息
	 * @param user_id
	 * @return
	 */
	int deleteByUser(Long user_id);

}
