package eon.hg.fap.core.query.query;

import eon.hg.fap.core.jpa.BaseRepository;
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
public class BaseQuery implements IQuery {

	private BaseRepository dao;

	private int begin;

	private int max;

	private Map params;

	/**
	 * 查询接口构造函数，构造一个基类查询
	 *
	 * @param dao
	 */
	public BaseQuery(BaseRepository dao) {
		this.dao = dao;
	}

	/**
	 * 根据查询条件查询结果(所有结果值)，返回结果数据集合List
	 */
	public List getResult(String construct, String condition) {
		// TODO Auto-generated method stub
		return dao.find(construct, condition, this.params, begin, max);
	}

	/**
	 * 根据查询条件查询对应区间的结果值，返回结果数据集合List
	 */
	public List getResult(String hql) {
		// TODO Auto-generated method stub
		return this.dao.query(hql, this.params, begin, max);
	}

	/**
	 * 根据查询条件查询结果总数，使用count(obj.id)来完成，该方法仅仅用在计算分页信息
	 */
	public int getRows(String hql) {
		// TODO Auto-generated method stub
		int n = hql.toLowerCase().indexOf("order by");
		Object[] params = null;
		if (n > 0) {
			hql = hql.substring(0, n);
		}
		List ret = dao.query(hql, this.params, 0, 0);
		if (ret != null && ret.size() > 0) {
			return ((Long) ret.get(0)).intValue();
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
		// TODO Auto-generated method stub
		this.params = params;
	}

	@Override
	public Map getParams() {
		// TODO Auto-generated method stub
		return this.params;
	}

}
