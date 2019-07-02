package eon.hg.fap.core.jpa;

import eon.hg.fap.common.util.metatype.Dto;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> extends Repository<T,ID> {
    Object get(Class clazz, Serializable id);
    List<Dto> findDtoBySql(final String nnq);
    List<Map> findBySql(final String nnq);
    List<Dto> findDtoBySql(final String nnq, final Map params);
    List<Map> findBySql(final String nnq, final Map params);
    List<Map> findBySql(final String nnq, final Map params,
                        final int begin, final int max);
    List<Dto> findDtoBySql(final String nnq, final Object[] params);
    List<Map> findBySql(final String nnq, final Object[] params);
    List<Map> findBySql(final String nnq, final Object[] params,
                        final int begin, final int max);
    int executeBySql(final String nnq);
    int executeBySql(final String nnq, final Object[] params);

}
