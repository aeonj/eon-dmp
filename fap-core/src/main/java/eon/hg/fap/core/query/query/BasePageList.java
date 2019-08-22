package eon.hg.fap.core.query.query;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.jpa.BaseRepository;
import eon.hg.fap.core.query.PageList;
import eon.hg.fap.core.query.support.IQuery;
import eon.hg.fap.core.query.support.IQueryObject;

import java.util.Map;

/**
 * 面向对象分页类，该类用来进行数据查询并分页返回数据信息
 * @author aeon
 * 
 */
public class BasePageList extends PageList {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6730593239674387757L;
	protected String construct;

	protected String scope;
	
	protected String fetchs;

	public BasePageList(Class cls, IQueryObject queryObject, BaseRepository dao) {
		this(cls, queryObject.getConstruct(), queryObject.getFetchs(), queryObject.getQuery(),
				queryObject.getParameters(), dao);
	}

	public BasePageList(Class cls, String construct, String scope,
						Map paras, BaseRepository dao) {
		this(cls,construct, "", scope, paras, dao);
	}

	/**
	 * 构造分页信息
	 * 
	 * @param cls
	 *            对应的实体类
	 * @param construct
	 *            查询构造函数，为空是查询所有字段，格式为 new Goods(id,goodsName)
	 * @param scope
	 *            查询条件
	 * @param paras
	 *            查询参数，采用占位符管理
	 * @param dao
	 *            对应的dao
	 */
	public BasePageList(Class cls, String construct, String fetchs, String scope,
						Map paras, BaseRepository dao) {
		this.cls = cls;
		this.scope = scope;
		this.construct = construct;
		this.fetchs = fetchs;
		IQuery query = new BaseQuery(dao);
		query.setParaValues(paras);
		this.setQuery(query);
	}

	/**
	 * 查询
	 * 
	 * @param currentPage
	 *            当前页数
	 * @param pageSize
	 *            一页的查询个数
	 */
	public void doList(int currentPage, int pageSize) {
		String totalSql = "select COUNT(obj.id) from " + cls.getName()
				+ " obj ";
		String clazzName = this.cls.getName();
		StringBuffer sb = null;
		if (construct != null && !construct.equals("")) {
			sb = new StringBuffer("select " + construct
					+ " from ");
		} else {
			sb = new StringBuffer("select obj from ");
		}
		sb.append(clazzName).append(" obj");
		sb.append(getFetchJoinStr());
		String querySql = sb.toString();
		super.doList(pageSize, currentPage, querySql, totalSql, scope);
	}

	private String getFetchJoinStr() {
		if (CommUtil.isEmpty(this.fetchs)) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			if (this.fetchs.indexOf("join ")>=0) {
				sb.append(" ").append(this.fetchs);
				return sb.toString();
			} else {
				for (String field : this.fetchs.split(",")) {
					sb.append(" left join fetch obj.").append(field);
				}
				return sb.toString();
			}
		}
	}
}
