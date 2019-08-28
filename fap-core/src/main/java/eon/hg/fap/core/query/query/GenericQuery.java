package eon.hg.fap.core.query.query;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.jpa.GenericRepository;
import eon.hg.fap.core.query.support.IQuery;

import java.util.List;
import java.util.Map;

/**
 * 
 * <p>
 * Title: GenericQuery.java
 * </p>
 * 
 * <p>
* 面向对象基础查询类，通过查询对象的封装完成查询信息
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * 
 */
public class GenericQuery implements IQuery {

	private GenericRepository dao;

	private int begin;

	private int max;

	private Map params;

	/**
	 * 查询接口构造函数，构造一个基类查询
	 *
	 * @param dao
	 */
	public GenericQuery(GenericRepository dao) {
		this.dao = dao;
	}

	/**
	 * 根据查询条件查询结果(所有结果值)，返回结果数据集合List
	 */
	public List getResult(String construct, String condition) {
		return null;
	}

	/**
	 * 根据查询条件查询对应区间的结果值，返回结果数据集合List
	 */
	public List getResult(String sql) {
		return this.dao.findDtoBySql(sql, this.params, begin, max);
	}

	/**
	 * 根据查询条件查询结果总数，使用count(obj.id)来完成，该方法仅仅用在计算分页信息
	 */
	public int getRows(String sql) {
		List<Map> ret = dao.findDtoBySql(sql, this.params);
		if (ret != null && ret.size() > 0) {
			return CommUtil.null2Int(ret.get(0).get("count_num"));
		} else {
			return 0;
		}
	}

	public void setFirstResult(int begin) {
		this.begin = begin;
	}

	public void setMaxResults(int max) {
		this.max = max;
	}

	@Override
	public void setParaValues(Map params) {
		this.params = params;
	}

	@Override
	public Map getParams() {
		return this.params;
	}

}
