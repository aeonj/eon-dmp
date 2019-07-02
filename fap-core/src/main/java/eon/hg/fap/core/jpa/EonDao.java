package eon.hg.fap.core.jpa;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * Title: GenericEntityDao.java
*  数据库操作基础DAO，系统使用JPA完成所有数据库操作，默认JPA的实现为Hibernate
 * 
 * @author aeon
 * 
 */
@Slf4j
@Component
public class EonDao {
	
	@Resource(name = "entityManagerPrimary")
	private EntityManager entityManager;

	@Autowired
	@Qualifier("primaryDataSource")
	private DataSource dataSource;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Object get(Class clazz, Serializable id) {
		if (id == null)
			return null;
		return this.entityManager.find(clazz, id);
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Dto> findDtoBySql(final String nnq) {
		return CommUtil.inListMapToDto(findBySql(nnq));
	}

	public List<Map> findBySql(final String nnq) {
		log.debug(nnq);
		Query query = this.entityManager.createNativeQuery(nnq);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

	public List<Dto> findDtoBySql(final String nnq, final Map params) {
		return CommUtil.inListMapToDto(findBySql(nnq,params));
	}

    public List<Map> findBySql(final String nnq, final Map params) {
        return findBySql(nnq,params,-1,-1);
    }

	public List<Map> findBySql(final String nnq, final Map params,
			final int begin, final int max) {
		log.debug(nnq);
		log.debug(params.toString());
		Query query = this.entityManager.createNativeQuery(nnq);
		if (params != null) {
			Iterator<String> its = params.keySet().iterator();
			while (its.hasNext()) {
				String key =its.next();
				query.setParameter(CommUtil.null2String(key),
						params.get(key));
			}
		}
		if (begin >= 0 && max > 0) {
			query.setFirstResult(begin);
			query.setMaxResults(max);
		}
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

	public List<Dto> findDtoBySql(final String nnq, final Object[] params) {
		return CommUtil.inListMapToDto(findBySql(nnq,params));
	}

	public List<Map> findBySql(final String nnq, final Object[] params) {
        return findBySql(nnq,params,-1,-1);
    }

	public List<Map> findBySql(final String nnq, final Object[] params,
			final int begin, final int max) {
		log.debug(nnq);
		log.debug(params.toString());
		Query query = this.entityManager.createNativeQuery(nnq);
		int parameterIndex = 1;
		if (params != null && params.length > 0) {
			for (Object obj : params) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		if (begin >= 0 && max > 0) {
			query.setFirstResult(begin);
			query.setMaxResults(max);
		}
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

	@Transactional
	public int executeBySql(final String nnq) {
		log.debug(nnq);
		Query query = this.entityManager.createNativeQuery(nnq);
		return query.executeUpdate();
	}

	@Transactional
	public int executeBySql(final String nnq, final Object[] params) {
		log.debug(nnq);
		log.debug(params.toString());
		Query query = this.entityManager.createNativeQuery(nnq);
		int parameterIndex = 1;
		if (params != null && params.length > 0) {
			for (Object obj : params) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		return query.executeUpdate();
	}

	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
