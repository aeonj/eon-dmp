package eon.hg.fap.core.jpa.support;

import cn.hutool.core.convert.Convert;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.core.jpa.GenericRepository;
import eon.hg.fap.core.query.PageObject;
import eon.hg.fap.core.query.query.GenericPageList;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.core.security.SecurityUserHolder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Date;
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
@Transactional(readOnly = true)
public class SimpleGenericRepository<T, ID extends Serializable> implements GenericRepository<T, ID> {

	private EntityManager entityManager;

	public SimpleGenericRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public <S extends T> S get(Class<S> clazz, Serializable id) {
		if (id == null)
			return null;
		return this.entityManager.find(clazz, id);
	}

	@Transactional
	public <S extends T> S save(S entity) {
		if (entity instanceof IdEntity) {
			if (CommUtil.isExistsAttr(entity.getClass(), "rg_code")) {
				Object obj = CommUtil.invokeGetMethod(entity, "rg_code");
				if (CommUtil.isEmpty(obj) || CommUtil.null2String(obj).equals(AeonConstants.SUPER_RG_CODE)) {
					CommUtil.invokeSetMethod(entity, "rg_code", SecurityUserHolder.getRgCode());
				}
			}
			if (SecurityUserHolder.getOnlineUser() != null) {
				CommUtil.invokeSetMethod(entity, "lastUser", SecurityUserHolder.getOnlineUser().getUsername());
			}
			CommUtil.invokeSetMethod(entity, "addTime", new Date());
			CommUtil.invokeSetMethod(entity, "lastTime", new Date());
		}
		entityManager.persist(entity);
		return entity;
	}

	@Transactional
	public <S extends T> S update(S entity) {
		if (entity instanceof IdEntity) {
			if (SecurityUserHolder.getOnlineUser() != null) {
				CommUtil.invokeSetMethod(entity, "lastUser", SecurityUserHolder.getOnlineUser().getUsername());
			}
			CommUtil.invokeSetMethod(entity, "lastTime", new Date());
		}
		return entityManager.merge(entity);
	}

	@Transactional
	public void remove(T entity) {
		Assert.notNull(entity, "Entity must not be null!");
		Object existing = entityManager.find(entity.getClass(), CommUtil.invokeGetMethod(entity,"id"));
		// if the entity to be deleted doesn't exist, delete is a NOOP
		if (existing == null) {
			return;
		}
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}

	@Override
	public <S extends T> S getBy(Class<S> clazz, Object... properties) {
		if (properties.length % 2!=0) {
			return null;
		}
		StringBuffer sb = new StringBuffer("select obj from ");
		sb.append(clazz.getName()).append(" obj");
		Query query = null;
		sb.append(" where obj.is_deleted=0");
		if (CommUtil.isExistsAttr(clazz, "rg_code")) {
			sb.append(" and obj.rg_code=:rg_code");
			query.setParameter("rg_code", SecurityUserHolder.getRgCode());
		}
		for (int i=0; i<properties.length; i++) {
			if (CommUtil.isEmpty(properties[i])) {
				return null;
			}
			String propertyName = CommUtil.null2String(properties[i]);
			i++;
			Object propertyValue = properties[i];
			if (propertyValue == null) {
				sb.append(" and obj.").append(propertyName)
						.append(" = :").append(propertyName);
				query.setParameter(propertyName, propertyValue);
			} else {
				sb.append(" and obj.").append(propertyName)
						.append(" is NULL");
			}
		}
		//query.setHint("org.hibernate.cacheable", true);
		List<S> ret = query.getResultList();
		if (ret != null && ret.size() == 1) {
			return ret.get(0);
		} else if (ret != null && ret.size() > 1) {
			throw new IllegalStateException(
					"worning  --more than one object find!!");
		} else {
			return null;
		}
	}

	@Override
	public List<T> query(String queryStr, Map params, int begin, int max) {
		Query query = this.entityManager.createQuery(queryStr);
		if (params != null && params.size() > 0) {
			for (Object key : params.keySet()) {
				//通过转换，解决querypanel查询视图自定义查询类型转换问题
				Object val = Convert.convert(query.getParameter(key.toString()).getParameterType(),params.get(key));
				query.setParameter(key.toString(), val);
			}
		}
		if (begin >= 0 && max > 0) {
			query.setFirstResult(begin);
			query.setMaxResults(max);
		}
		return query.getResultList();
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

    public List<Dto> findDtoBySql(final String nnq, final Map params,
								  final int begin, final int max) {
        return CommUtil.inListMapToDto(findBySql(nnq,params,begin,max));
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

	public List<Dto> findDtoBySql(final String nnq, final Object[] params,
								  final int begin, final int max) {
		return CommUtil.inListMapToDto(findBySql(nnq,params,begin,max));
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
