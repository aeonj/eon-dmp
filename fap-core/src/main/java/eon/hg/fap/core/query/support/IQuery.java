package eon.hg.fap.core.query.support;

import java.util.List;
import java.util.Map;

/**
 * 
 * <p>
 * Title: IQuery.java
 * </p>
 * 
 * <p>
*  分页查询算法接口
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * 
 */
public interface IQuery {
	/**
	 * 根据查询条件返回记录总数
	 * 
	 * @param hql
	 * @return 查询记录结果总数
	 */
	int getRows(String hql);

	/**
	 * 根据查询条件返回符合条件的结果数
	 * 
	 * @param condition
	 *            查询条件
	 * @params construct 查询构造函数
	 * @return 根据条件获得查询结果集
	 */
	List getResult(String construct, String condition);

	/**
	 * 根据查询条件返回符合条件的结果数
	 * 
	 * @param hql
	 *            查询语句
	 * @return 根据条件获得查询结果集
	 */
	List getResult(String hql);

	/**
	 * 设置有效结果记录的开始位置
	 * 
	 * @param begin
	 */
	void setFirstResult(int begin);

	/**
	 * 最大返回记录数
	 * 
	 * @param max
	 */
	void setMaxResults(int max);

	/**
	 * 设置查询参数
	 * 
	 * @param params
	 */
	void setParaValues(Map params);
	
	Map getParams();

}
