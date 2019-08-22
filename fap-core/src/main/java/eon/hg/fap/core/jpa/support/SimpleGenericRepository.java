package eon.hg.fap.core.jpa.support;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.jpa.GenericRepository;
import eon.hg.fap.core.query.PageObject;
import eon.hg.fap.core.query.query.GenericPageList;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
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
public class SimpleGenericRepository<T, ID extends Serializable> implements GenericRepository<T, ID> {

	private EntityManager entityManager;

	public SimpleGenericRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Object get(Class clazz, Serializable id) {
		if (id == null)
			return null;
		return this.entityManager.find(clazz, id);
	}

	public List<Dto> findDtoBySql(final String nnq) {
		return CommUtil.inListMapToDto(findBySql(nnq));
	}

	public List<Map> findBySql(final String nnq) {
		log.debug(nnq);
		Query query = this.entityManager.createNativeQuery(nnq);
		//此处query若是代理对象，会出现不能转换到NativeQueryImpl的错误，暂时考虑在Service服务加事务标签解决
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

	@Override
	public IPageList list(IQueryObject properties) {
		if (properties == null) {
			return null;
		}
		GenericPageList pList = new GenericPageList(properties, this);
		PageObject pageObj = properties.getPageObj();
		if (pageObj != null) {
			pList.doList(pageObj.getCurrentPage(),pageObj.getPageSize());
		} else {
			pList.doList(0,-1);
		}
		return pList;
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

}
