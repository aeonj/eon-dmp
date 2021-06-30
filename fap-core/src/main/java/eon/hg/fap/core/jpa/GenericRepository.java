package eon.hg.fap.core.jpa;

import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> extends Repository<T,ID> {
    <S extends T> S get(Class<S> clazz, Serializable id);
    <S extends T> S save(S entity);
    <S extends T> S update(S entity);
    void remove(T entity);
    <S extends T> S  getBy(Class<S> clazz, Object... properties);

    List<T> query(String queryStr, Map params, int begin, int max);

    List<Dto> findDtoBySql(final String nnq);
    List<Map> findBySql(final String nnq);
    List<Dto> findDtoBySql(final String nnq, final Map params);
    List<Map> findBySql(final String nnq, final Map params);
    List<Dto> findDtoBySql(final String nnq, final Map params,
                           final int begin, final int max);
    List<Map> findBySql(final String nnq, final Map params,
                        final int begin, final int max);
    List<Dto> findDtoBySql(final String nnq, final Object[] params);
    List<Dto> findDtoBySql(final String nnq, final Object[] params,
                                  final int begin, final int max);
    List<Map> findBySql(final String nnq, final Object[] params);
    List<Map> findBySql(final String nnq, final Object[] params,
                        final int begin, final int max);
    IPageList list(IQueryObject properties);
    int executeBySql(final String nnq);
    int executeBySql(final String nnq, final Object[] params);

}
