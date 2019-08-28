package eon.hg.fap.core.query.query;

import cn.hutool.core.util.StrUtil;
import eon.hg.fap.core.jpa.GenericRepository;
import eon.hg.fap.core.query.PageList;
import eon.hg.fap.core.query.support.IQuery;
import eon.hg.fap.core.query.support.IQueryObject;

import java.util.Map;

/**
 * 面向对象分页类，该类用来进行数据查询并分页返回数据信息
 *
 * @author aeon
 */
public class GenericPageList extends PageList {

    protected String genericSql;

    protected String scope;

    protected boolean isAllSql;

    public GenericPageList(IQueryObject queryObject, GenericRepository dao) {
        this(queryObject.getNativeSql(), queryObject.getQuery(), queryObject.getParameters(), queryObject.isAllSql(), dao);
    }

    public GenericPageList(String genericSql, String scope, Map params, boolean isAllSql, GenericRepository dao) {
        this.genericSql = genericSql;
        this.scope = scope;
        this.isAllSql = isAllSql;
        IQuery query = new GenericQuery(dao);
        query.setParaValues(params);
        this.setQuery(query);
    }

    /**
     * 查询
     *
     * @param currentPage 当前页数
     * @param pageSize    一页的查询个数
     */
    public void doList(int currentPage, int pageSize) {
        String querySql;
        if (isAllSql) {
            int iOrder = StrUtil.indexOfIgnoreCase(genericSql, " order by ");
            if (iOrder >= 0) {
                querySql = genericSql.substring(0, iOrder);
            } else {
                querySql = genericSql;
            }
            int iFrom = StrUtil.indexOfIgnoreCase(querySql, " from ");
            String sFromSql = querySql.substring(iFrom);
            String totalSql = "select count(1) as count_num " + sFromSql;
            super.doList(pageSize, currentPage, genericSql, totalSql, "");
        } else {
            int iWhere = StrUtil.lastIndexOfIgnoreCase(genericSql, " where ");
            if (iWhere >= 0) {
                querySql = genericSql.substring(0, iWhere);
                int iOrder = StrUtil.indexOfIgnoreCase(genericSql, " order by ");
                if (iOrder >= 0) {
                    String sWhere = genericSql.substring(iWhere + 7, iOrder);
                    String sOrder = genericSql.substring(iOrder);
                    this.scope = sWhere + " and " + this.scope + sOrder;
                } else {
                    this.scope = genericSql.substring(iWhere + 7) + " and " + this.scope;
                }
            } else {
                int iOrder = StrUtil.indexOfIgnoreCase(genericSql, " order by ");
                if (iOrder >= 0) {
                    querySql = genericSql.substring(0, iOrder);
                    String sOrder = genericSql.substring(iOrder);
                    this.scope = this.scope + sOrder;
                } else {
                    querySql = genericSql;
                }
            }
            int iFrom = StrUtil.indexOfIgnoreCase(querySql, " from ");
            String sFromSql = querySql.substring(iFrom);
            String totalSql = "select count(1) as count_num " + sFromSql;
            super.doList(pageSize, currentPage, querySql, totalSql, this.scope);
        }
    }

}
