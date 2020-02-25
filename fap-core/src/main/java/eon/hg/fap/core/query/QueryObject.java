package eon.hg.fap.core.query;

import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.domain.virtual.SysMap;
import eon.hg.fap.core.query.support.IQueryObject;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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

	protected String queryString = "";

	protected String fetchs = "";

	protected String nativeSql;   //原生sql语句

	protected boolean isAllSql;    //是否全sql，包括条件也是sql

	private String alias = "obj";

	private WhereObject whereObj = new WhereObject();

	private Object object;

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

	public void setParams(Map params) {
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

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
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
	 * 获取原生sql语句
	 *
	 * @return
	 */
	@Override
	public String getNativeSql() {
		return nativeSql;
	}

	@Override
	public boolean isAllSql() {
		return isAllSql;
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
	 * 创建原生sql查询实例
	 * @param nativeSql
	 * @return
	 */
	public static QueryObject SqlCreate(String nativeSql) {
		return SqlCreate(nativeSql,true);
	}

	public static QueryObject SqlCreate(String nativeSql, boolean isAllSql) {
		return SqlCreate(nativeSql,null, isAllSql);
	}
	/**
	 * 创建原生sql查询实例
	 * @param nativeSql
	 * @param params
	 * @return
	 */
	public static QueryObject SqlCreate(String nativeSql, Map params) {
		return SqlCreate(nativeSql, params, true);
	}

	public static QueryObject SqlCreate(String nativeSql, Map params, boolean isAllSql) {
		QueryObject qo = new QueryObject();
		qo.nativeSql = nativeSql;
		if (params!=null) {
			qo.params = params;
		}
		qo.isAllSql=isAllSql;
		return qo;
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
		return getCondionStr() + whereObj.toString()+ orderString();
	}

	protected String orderString() {
		StringBuilder orderString = new StringBuilder();
		if (this.getOrderBy() != null && !"".equals(getOrderBy())) {
			orderString.append(" order by ");
			if (alias.equals("")) {
				orderString.append(this.getOrderBy());
			} else {
				String[] orderArr = CommUtil.splitByChar(this.getOrderBy(),",");
				for (int i=0;i<orderArr.length;i++) {
					String order_ele = orderArr[i];
					if (i>0)
						orderString.append(",");
					orderString.append(alias).append(".").append(order_ele.trim());
				}
			}
		}
		if (this.getOrderType() != null && !"".equals(getOrderType())) {
			orderString.append(" ").append(getOrderType());
		}
		return orderString.toString();
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

	/**
	 * 继承于F3
	 * @return
	 */
	public IQueryObject and(String key, String operation, String value) {
		whereObj.and(key,operation,value);
		return this;
	}

	public IQueryObject andPlusBracket(String key, String operation, String value) {
		whereObj.andPlusBracket(key,operation,value);
		return this;
	}

	public IQueryObject or(String key, String operation, String value) {
		whereObj.or(key,operation,value);
		return this;
	}

	public IQueryObject orPlusBracket(String key, String operation, String value) {
		whereObj.orPlusBracket(key,operation,value);
		return this;
	}

	public IQueryObject addBracketFront() {
		whereObj.addBracketFront();
		return this;
	}

	public IQueryObject addBracketBack() {
		whereObj.addBracketBack();
		return this;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<QueryBean> getWhereList() {
		return whereObj.getWhereList();
	}

	public String getCondionStr() {
		customizeQuery();
		String condition = queryString;
		if (StrUtil.isEmpty(queryString)) {
			return "1=1 ";
		} else {
			int iAnd = StrUtil.indexOfIgnoreCase(condition," and ");
			if (iAnd!=-1) {
				condition = condition.substring(iAnd+5);
			}
			return condition;
		}
	}
	///////////////////F3结束

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

	private class WhereObject {
		private List<QueryBean> whereList;

		public void addWhereList(QueryBean bean) {
			if (whereList == null) {
				whereList = new ArrayList();
			}
			whereList.add(bean);
		}

		public WhereObject and(String key, String operation, String value) {
			QueryBean bean = new QueryBean();
			bean.setRelation("and");
			bean.setBracketFront(false);
			bean.setKey(key);
			bean.setOperation(operation);
			bean.setValue(value);
			bean.setBracketBack(false);
			addWhereList(bean);
			return this;
		}

		public WhereObject andPlusBracket(String key, String operation, String value) {
			QueryBean bean = new QueryBean();
			bean.setRelation("and");
			bean.setBracketFront(true);
			bean.setKey(key);
			bean.setOperation(operation);
			bean.setValue(value);
			bean.setBracketBack(false);
			addWhereList(bean);
			return this;
		}

		public WhereObject or(String key, String operation, String value) {
			QueryBean bean = new QueryBean();
			bean.setRelation("or");
			bean.setBracketFront(false);
			bean.setKey(key);
			bean.setOperation(operation);
			bean.setValue(value);
			bean.setBracketBack(false);
			addWhereList(bean);
			return this;
		}

		public WhereObject orPlusBracket(String key, String operation, String value) {
			QueryBean bean = new QueryBean();
			bean.setRelation("or");
			bean.setBracketFront(true);
			bean.setKey(key);
			bean.setOperation(operation);
			bean.setValue(value);
			bean.setBracketBack(false);
			addWhereList(bean);
			return this;
		}

		public WhereObject addBracketFront() {
			QueryBean bean = new QueryBean();
			bean.setBracketFront(true);
			bean.setOnlyBracket(true);
			addWhereList(bean);
			return this;
		}

		public WhereObject addBracketBack() {
			QueryBean bean = new QueryBean();
			bean.setBracketBack(true);
			bean.setOnlyBracket(true);
			addWhereList(bean);
			return this;
		}

		public List<QueryBean> getWhereList() {
			return whereList;
		}

		public void setWhereList(List<QueryBean> whereList) {
			this.whereList = whereList;
		}

		public String toString() {
			if (whereList == null || whereList.size() == 0) {
				return "";
			}

			StringBuffer sb = new StringBuffer();
			for (QueryBean bean : whereList) {
				if (bean.isOnlyBracket()) {
					sb.append(" ");
					sb.append(bean.isBracketFront() ? "(" : "");
					sb.append(bean.isBracketBack() ? ")" : "");
					sb.append(" ");
				} else if (alias.equals("")) {
					sb.append(" ");
					sb.append(bean.getRelation());
					sb.append(" ");
					sb.append(bean.isBracketFront() ? "(" : "");
					sb.append(" ");
					sb.append(bean.getKey());
					sb.append(" ");
					sb.append(bean.getOperation());
					sb.append(" ");
					sb.append(bean.getValue());
					sb.append(bean.isBracketBack() ? ")" : "");
					sb.append(" ");
				} else {
					sb.append(" ");
					sb.append(bean.getRelation());
					sb.append(" ");
					sb.append(bean.isBracketFront() ? "(" : "");
					sb.append(" ");
					sb.append(getAlias() + "." + bean.getKey());
					sb.append(" ");
					sb.append(bean.getOperation());
					sb.append(" ");
					sb.append(bean.getValue());
					sb.append(bean.isBracketBack() ? ")" : "");
					sb.append(" ");
				}
			}
			return sb.toString();
		}
	}

}
