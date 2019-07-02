package eon.hg.fap.core.jpa.support;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.jpa.BaseRepository;
import eon.hg.fap.core.query.GenericPageList;
import eon.hg.fap.core.query.PageObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.core.security.SecurityUserHolder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * dao封装类
 * @param <T>
 * @param <ID>
 */
public class SimpleBaseRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager entityManager;
    private Class<T> entityClass;

    public SimpleBaseRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    public SimpleBaseRepository(Class<T> domainClass, EntityManager em) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
    }

    @Override
    public T get(ID id) {
        Optional<T> optional = this.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public T getBy(String propertyName, Object value) {
        Specification<T> sf = (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate finalConditions = criteriaBuilder.conjunction();
            if (CommUtil.isExistsAttr(getDomainClass(),"rg_code")) {
                finalConditions = criteriaBuilder.and(finalConditions, criteriaBuilder.equal(root.get("rg_code"),SecurityUserHolder.getRgCode()));
            }
            if (StringUtils.isEmpty(propertyName) || value==null) {
                return null;
            }
            finalConditions = criteriaBuilder.and(finalConditions, criteriaBuilder.equal(root.get(propertyName),value));
            return finalConditions;
        };
        return this.findOne(sf).get();
    }

    @Override
    public T getBy(String construct, String propertyName, Object value) {
        return getBy(construct,propertyName,value,CommUtil.isExistsAttr(getDomainClass(),"rg_code"));
    }

    /**
     * 根据查询条件查出对应的数据
     *
     * @param construct 查询构造函数，如果不存在则默认使用obj查询所有字段，可以使用new
     *                  Element(id,elename)来查询指定字段，提高系统性能，此时需要在Element中增加对应的构造函数
     * @param queryStr     查询的条件，使用位置参数，对象名统一为obj，查询条件从where后开始。 obj.id=:id and
     *                  obj.userRole=:role
     * @param params    查询语句中的参数，使用Map传递，结合查询语句中的参数命名来确定 Map map=new HashMap();
     *                  map.put("id",id); map.put("role",role); 该方法的使用方法为: query(
     *                  "select obj from User obj where obj.id=:id and obj.userRole=:role order by obj.addTime desc"
     *                  ,map,1,20);
     * @param begin     查询数据的起始位置
     * @param max       查询数据的最大值
     * @return 数据列表
     */
    @Override
    public List<T> find(String construct, String queryStr, Map params, int begin, int max) {
        return find(construct,"",queryStr,params,begin,max);
    }

    private List<T> find(String construct, String fetchStr, String queryStr, Map params, int begin, int max) {
        String clazzName = getDomainClass().getName();
        StringBuffer sb = null;
        if (construct != null && !construct.equals("")) {
            sb = new StringBuffer("select " + construct
                    + " from ");
        } else {
            sb = new StringBuffer("select obj from ");
        }
        sb.append(clazzName).append(" obj ");
        sb.append(fetchStr);
        boolean filterRg = !params.containsKey("rg_code");
        if (filterRg) {
            filterRg = CommUtil.isExistsAttr(getDomainClass(), "rg_code");
        }
        if (filterRg) {
            sb.append(" where obj.rg_code=:rg_code and ");
        } else {
            sb.append(" where ");
        }
        if (CommUtil.isExistsAttr(getDomainClass(), "is_deleted")) {
            sb.append("obj.is_deleted=0 and ");
        }
        sb.append(queryStr);
        Query query = this.entityManager.createQuery(sb.toString(), getDomainClass());
        for (Object key : params.keySet()) {
            query.setParameter(key.toString(), params.get(key));
        }
        if (filterRg) {
            query.setParameter("rg_code", SecurityUserHolder.getRgCode());
        }
        if (begin >= 0 && max > 0) {
            query.setFirstResult(begin);
            query.setMaxResults(max);
        }
        return query.getResultList();
    }

    /**
     * 根据一个查询条件及其参数，还有开始查找的位置和查找的个数来查找任意类型的对象。
     *
     * @param queryStr  完整的查询语句，使用命名参数。比如：select user from User user where user.name =
     *               :name and user.properties = :properties
     * @param params 查询条件中的参数的值。使用Map
     * @param begin  开始查询的位置
     * @param max    需要查询的对象的个数
     * @return 一个任意对象的List对象，如果没有查到任何数据，返回一个空的List对象。
     */
    @Override
    public List<T> query(String queryStr, Map params, int begin, int max) {
        Query query = this.entityManager.createQuery(queryStr);
        if (params != null && params.size() > 0) {
            for (Object key : params.keySet()) {
                query.setParameter(key.toString(), params.get(key));
            }
        }
        if (begin >= 0 && max > 0) {
            query.setFirstResult(begin);
            query.setMaxResults(max);
        }
        return query.getResultList();
    }

    @Override
    public List<T> find(IQueryObject properties) {
        if (properties == null) {
            return null;
        }
        String fetchs = properties.getFetchs();
        String query = properties.getQuery();
        String construct = properties.getConstruct();
        Map params = properties.getParameters();
        return this.find(construct,fetchs,query,params,-1,-1);
    }

    @Override
    public IPageList list(IQueryObject properties) {
        if (properties == null) {
            return null;
        }
        GenericPageList pList = new GenericPageList(getDomainClass(), properties, this);
        if (properties != null) {
            PageObject pageObj = properties.getPageObj();
            if (pageObj != null)
                pList.doList(pageObj.getCurrentPage() == null ? 0 : pageObj
                        .getCurrentPage(), pageObj.getPageSize() == null ? 0
                        : pageObj.getPageSize());
        } else
            pList.doList(0, -1);
        return pList;
    }

    @Override
    public List<T> findBySql(String sql) {
        Query query;
        if (Map.class.isAssignableFrom(getTClass())) {
            query = this.entityManager.createNativeQuery(sql);
        } else {
            query = this.entityManager.createNativeQuery(sql,getDomainClass());
        }
        return query.getResultList();
    }

    /**
     * 获取T泛型对应的类型
     * @return
     */
    private Class<T> getTClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    @Override
    public List<T> findBySql(String sql, Object[] params) {
        return findBySql(sql, params,0,0);
    }

    @Override
    public List<T> findBySql(String sql, Object[] params, int begin, int max) {
        Query query = this.entityManager.createNativeQuery(sql,getDomainClass());
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
        return query.getResultList();
    }

    @Transactional
    public <S extends T> S save(S entity) {
        if (CommUtil.isExistsAttr(getDomainClass(), "rg_code")) {
            Object obj = CommUtil.invokeGetMethod(entity, "rg_code");
            if (CommUtil.isEmpty(obj) || CommUtil.null2String(obj).equals(AeonConstants.SUPER_RG_CODE)) {
                CommUtil.invokeSetMethod(entity, "rg_code", SecurityUserHolder.getRgCode());
            }
        }
        if (SecurityUserHolder.getCurrentUser()!=null) {
            CommUtil.invokeSetMethod(entity, "lastUser", SecurityUserHolder.getCurrentUser().getUsername());
        }
        CommUtil.invokeSetMethod(entity, "addTime", new Date());
        CommUtil.invokeSetMethod(entity, "lastTime", new Date());
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public <S extends T> S update(S entity) {
        if (SecurityUserHolder.getCurrentUser()!=null) {
            CommUtil.invokeSetMethod(entity, "lastUser", SecurityUserHolder.getCurrentUser().getUsername());
        }
        CommUtil.invokeSetMethod(entity, "lastTime", new Date());
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public void remove(ID id) {
        deleteById(id);
    }

    @Override
    public void remove(T entity) {
        delete(entity);
    }

    @Override
    public int executeQuery(final String jpql) {
        Query query = this.entityManager.createQuery(jpql);
        return query.executeUpdate();
    }

    @Override
    public int executeQuery(final String jpql, final Object[] params) {
        Query query = this.entityManager.createQuery(jpql);
        int parameterIndex = 1;
        if (params != null && params.length > 0) {
            for (Object obj : params) {
                query.setParameter(parameterIndex++, obj);
            }
        }
        return query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = false)
    public int executeBySql(String sql) {
        Query query = this.entityManager.createNativeQuery(sql);
        return query.executeUpdate();
    }

    @Override
    public int executeBySql(String sql, Object[] params) {
        Query query = this.entityManager.createNativeQuery(sql);
        int parameterIndex = 1;
        if (params != null && params.length > 0) {
            for (Object obj : params) {
                query.setParameter(parameterIndex++, obj);
            }
        }
        return query.executeUpdate();
    }

    protected T getBy(String construct, String propertyName, Object value, boolean filterRg) {
        StringBuffer sb = null;
        if (construct != null && !construct.equals("")) {
            sb = new StringBuffer("select " + construct
                    + " from ");
        } else {
            sb = new StringBuffer("select obj from ");
        }
        sb.append(getDomainClass().getName()).append(" obj");
        Query query = null;
        if (propertyName != null && value != null) {
            sb.append(" where obj.").append(propertyName)
                    .append(" = :value");
            sb.append(" and obj.is_deleted=0");
            if (filterRg) {
                sb.append(" and obj.rg_code=:rg_code");
                query = this.entityManager.createQuery(sb.toString());
                query.setParameter("value", value);
                query.setParameter("rg_code", SecurityUserHolder.getRgCode());
            } else {
                query = this.entityManager.createQuery(sb.toString()).setParameter(
                        "value", value);
            }
        } else {
            if (filterRg) {
                sb.append(" where obj.rg_code = :rg_code");
                sb.append(" and obj.is_deleted=0");
                query = this.entityManager.createQuery(sb.toString()).setParameter("rg_code", SecurityUserHolder.getRgCode());
            } else {
                sb.append(" where obj.is_deleted=0");
                query = this.entityManager.createQuery(sb.toString());
            }

        }
        //query.setHint("org.hibernate.cacheable", true);
        List ret = query.getResultList();
        if (ret != null && ret.size() == 1) {
            return (T) ret.get(0);
        } else if (ret != null && ret.size() > 1) {
            throw new IllegalStateException(
                    "worning  --more than one object find!!");
        } else {
            return null;
        }


    }

    @Override
    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    protected Class<T> getDomainClass() {
        if (this.entityClass==null) {
            return entityInformation.getJavaType();
        } else {
            return this.entityClass;
        }
    }

}
