package eon.hg.fap.core.query;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.domain.virtual.SysMap;
import eon.hg.fap.core.query.support.IQueryObject;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础查询对象，封装基础查询条件，包括页大小、当前页、排序信息等
 * @author AEON
 *
 */
public class QueryObject implements IQueryObject {

	private String construct;// 查询构造器，为空时查询obj所有字段

	protected Integer pageSize = 12;// 默认分页数据，表示每页12条记录

	protected Integer currentPage = 0;// 当前页，默认为0，jpa查询从0开始计算，表示第一页

	protected String orderBy;// 排序字段，默认为addTime

	protected String orderType;// 排序类型，默认为倒叙

	protected Map params = new HashMap();

	protected String queryString = "1=1";
	
	protected String fetchs = "";

	public String getConstruct() {
		return construct;
	}

	public void setConstruct(String construct) {
		this.construct = construct;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	protected void setParams(Map params) {
		this.params = params;
	}

	public String getOrderType() {
		return orderType;
	}

	public Integer getCurrentPage() {
		if (currentPage == null) {
			currentPage = -1;
		}
		return currentPage;
	}

	public String getOrder() {
		return orderType;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public Integer getPageSize() {
		if (pageSize == null) {
			pageSize = -1;
		}
		return pageSize;
	}

	/**
	 * @return the fetchs
	 */
	public String getFetchs() {
		return fetchs;
	}

	/**
	 * @param fetchs the fetchs to set
	 */
	public void setFetchs(String fetchs) {
		this.fetchs = fetchs;
	}

	public QueryObject() {

	}
	
	public QueryObject(String orderBy, String orderType) {
		this(null,orderBy,orderType);
	}


	public QueryObject(String construct, String orderBy, String orderType) {
		if (construct != null && !construct.equals("")) {
			this.setConstruct(construct);
		}
		this.setCurrentPage(-1);
		this.setPageSize(-1);
		if (orderBy == null || orderBy.equals("")) {
			this.setOrderBy("addTime");
		} else {
			this.setOrderBy(orderBy);
		}
		if (orderType == null || orderType.equals("")) {
			this.setOrderType("desc");
		} else {
			this.setOrderType(orderType);
		}
	}

	/**
	 * 可用于jGrid的请求
	 * @param construct
	 * @param orderBy 排序字段
	 * @param orderType 排序类型
	 * @param currPage 当前页
	 * @param pageSize 每页记录数
	 */
	public QueryObject(String construct, String orderBy, String orderType, Integer currPage, Integer pageSize) {
		if (construct != null && !construct.equals("")) {
			this.setConstruct(construct);
		}
		if (currPage != null && !currPage.equals("")) {
			this.setCurrentPage(CommUtil.null2Int(currPage));
		}
		if (pageSize != null && !pageSize.equals("")) {
			this.setPageSize(CommUtil.null2Int(pageSize));
		} else {
			this.setPageSize(this.pageSize);
		}
		if (orderBy == null || orderBy.equals("")) {
			this.setOrderBy("addTime");
		} else {
			this.setOrderBy(orderBy);
		}
		if (orderType == null || orderType.equals("")) {
			this.setOrderType("desc");
		} else {
			this.setOrderType(orderType);
		}
	}

	/**
	 * 构造一个queryObject
	 * 
	 * @param construct
	 *            查询对象构造函数，如new Goods(id,goodsName)
	 * @param currentPage
	 *            当前页
	 * @param mv
	 *            需要封装的视图
	 * @param orderBy
	 *            排序字段
	 * @param orderType
	 *            排序类型
	 */
	public QueryObject(String construct, String currentPage, ModelAndView mv,
			String orderBy, String orderType) {
		if (construct != null && !construct.equals("")) {
			this.setConstruct(construct);
		}
		if (currentPage != null && !currentPage.equals("")) {
			this.setCurrentPage(CommUtil.null2Int(currentPage));
		}
		this.setPageSize(this.pageSize);
		if (orderBy == null || orderBy.equals("")) {
			this.setOrderBy("addTime");
			mv.addObject("orderBy", "addTime");
		} else {
			this.setOrderBy(orderBy);
			mv.addObject("orderBy", orderBy);
		}
		if (orderType == null || orderType.equals("")) {
			this.setOrderType("desc");
			mv.addObject("orderType", "desc");
		} else {
			this.setOrderType(orderType);
			mv.addObject("orderType", orderType);
		}
	}

	/**
	 * 构造一个查询对象queryObject
	 * 
	 * @param currentPage
	 *            当前页
	 * @param mv
	 *            返回的视图
	 * @param orderBy
	 *            排序字段
	 * @param orderType
	 *            排序类型
	 */
	public QueryObject(String currentPage, ModelAndView mv, String orderBy,
                       String orderType) {
		if (currentPage != null && !currentPage.equals("")) {
			this.setCurrentPage(CommUtil.null2Int(currentPage));
		}
		this.setPageSize(this.pageSize);
		if (orderBy == null || orderBy.equals("")) {
			this.setOrderBy("addTime");
			mv.addObject("orderBy", "addTime");
		} else {
			this.setOrderBy(orderBy);
			mv.addObject("orderBy", orderBy);
		}
		if (orderType == null || orderType.equals("")) {
			this.setOrderType("desc");
			mv.addObject("orderType", "desc");
		} else {
			this.setOrderType(orderType);
			mv.addObject("orderType", orderType);
		}
	}

	/**
	 * 获取一个分页信息
	 * 
	 * @return 分页信息
	 */
	public PageObject getPageObj() {
		PageObject pageObj = new PageObject();
		pageObj.setCurrentPage(this.getCurrentPage());
		pageObj.setPageSize(this.getPageSize());
		if (this.currentPage == null || this.currentPage <= 0) {
			pageObj.setCurrentPage(1);
		}
		return pageObj;
	}

	public String getQuery() {
		customizeQuery();
		return queryString + orderString();
	}

	protected String orderString() {
		String orderString = " ";
		if (this.getOrderBy() != null && !"".equals(getOrderBy())) {
			orderString += " order by obj." + this.getOrderBy();
		}
		if (this.getOrderType() != null && !"".equals(getOrderType())) {
			orderString = orderString + " " + getOrderType();
		}
		return orderString;
	}

	public Map getParameters() {
		return this.params;
	}

	public IQueryObject addQuery(String field, SysMap para, String expression) {
		return addQuery(field,para,expression,"and");
	}

	public IQueryObject addQuery(String field, SysMap para, String expression,
                                 String logic) {
		if (field != null && para != null) {
			queryString += " " + logic + " " + field + " "
					+ handleExpression(expression) + ":"
					+ para.getKey().toString();
			params.put(para.getKey(), para.getValue());
		}
		return this;
	}

	public IQueryObject addQuery(String scope, Map paras) {
		if (scope != null) {
			if (scope.trim().indexOf("and") == 0
					|| scope.trim().indexOf("or") == 0) {
				queryString += " " + scope;
			} else
				queryString += " and " + scope;
			if (paras != null && paras.size() > 0) {
				for (Object key : paras.keySet()) {
					params.put(key, paras.get(key));
				}
			}
		}
		return this;
	}

	@Override
	public IQueryObject addQuery(String field, String para, Object obj,
                                 String expression) {
		return addQuery(field,para,obj,expression,"and");
	}

	public IQueryObject addQuery(String field, String para, Object obj,
                                 String expression, String logic) {
		if (field != null && para != null) {
			queryString += " " + logic + " "
					+ field + " " + expression + ":" + para;
			params.put(para, obj);
		}
		return this;
	}

	@Override
	public IQueryObject clearQuery() {
		// TODO Auto-generated method stub
		queryString = "1=1 ";
		params.clear();
		return this;
	}

	private String handleExpression(String expression) {
		if (expression == null)
			return "=";
		else
			return expression;
	}

	public void customizeQuery() {

	}

	@Override
	public void autoFilterRg(boolean filter) {
		this.params.put("allrg", !filter);
	}

}
