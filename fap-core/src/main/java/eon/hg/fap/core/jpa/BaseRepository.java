package eon.hg.fap.core.jpa;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepositoryImplementation<T,ID> {

    /**
     * 根据Id查找一个类型为T的对象。
     * @param id 传入的ID的值
     * @return 一个类型为T的对象
     */
    T get(ID id);

    T getBy(String propertyName, Object value) ;

    T getBy(String construct, String propertyName, Object value) ;

    /**
     * 根据查询条件查出对应的数据
     *
     * @param construct
     *            查询构造函数，如果不存在则默认使用obj查询所有字段，可以使用new
     *            Element(id,elename)来查询指定字段，提高系统性能，此时需要在Element中增加对应的构造函数
     * @param query
     *            查询的条件，使用位置参数，对象名统一为obj，查询条件从where后开始。 obj.id=:id and
     *            obj.userRole=:role
     * @param params
     *            查询语句中的参数，使用Map传递，结合查询语句中的参数命名来确定 Map map=new HashMap();
     *            map.put("id",id); map.put("role",role);
     * @param begin
     *            查询数据的起始位置
     * @param max
     *            查询数据的最大值
     * @return 数据列表
     */
    List<T> find(String construct, String query, Map params, int begin, int max);

    /**
     * 根据一个查询条件及其参数，还有开始查找的位置和查找的个数来查找任意类型的对象。
     * @param query
     *            完整的查询语句，使用命名参数。比如：select user from User user where user.name =
     *            :name and user.properties = :properties
     * @param params
     *            查询条件中的参数的值，使用Map传递，结合查询语句中的参数命名来确定 Map map=new HashMap();
     *            map.put("id",id); map.put("role",role); 使用Map该方法的使用方法为: query(
     *            "select obj from User obj where obj.id=:id and obj.userRole=:role order by obj.addTime desc"
     *            ,map,1,20);
     * @param begin
     *            开始查询的位置
     * @param max
     *            需要查询的对象的个数
     * @return 一个任意对象的List对象，如果没有查到任何数据，返回一个空的List对象。
     */
    List<T> query(String query, Map params, int begin, int max);

    List<T> find(IQueryObject properties);

    IPageList list(IQueryObject properties);

    /**
     * 自定义数据库原始sql语句查询
     * @param sql 查询sql语句
     * @return
     */
    List<T> findBySql(String sql);

    /**
     * 自定义数据库原始sql语句查询
     * @param sql 查询sql语句
     * @param params 对应的参数
     * @return
     */
    List<T> findBySql(String sql, Object[] params);

    /**
     * 自定义数据库原始sql语句查询，支持分页查询
     * @param sql 查询sql语句
     * @param params 查询条件中的参数的值。使用Object数组，要求顺序和查询条件中的参数位置一致。
     * @param begin 开始查询的位置
     * @param max 需要查询的对象的个数
     * @return 一个指定对象的List对象，如果没有查到任何数据，返回一个空的List对象。
     */
    List<T> findBySql(String sql, Object[] params, int begin, int max);

    /**
     * 持久化一个对象，该对象类型为T。
     * @param entity 需要持久化的对象，使用JPA标注。
     */
    <S extends T> S  save(S entity);

    /**
     * 更新一个对象，主要用于更新一个在persistenceContext之外的一个对象。
     * @param entity 需要更新的对象，该对象不需要在persistenceContext中。
     */
    <S extends T> S  update(S entity);

    /**
     * 根据对象id删除一个对象，该对象类型为T,等同于deleteById
     * @param id 需要删除的对象的id。
     */
    void remove(ID id);

    /**
     * 删除对象，等同于delete函数
     * @param entity
     */
    void remove(T entity);

    /**
     * 使用hql进行表操作
     * @param jpsql
     * @return
     */
    int executeQuery(String jpsql);

    /**
     * 使用hql进行表操作，带参数
     * @param jpsql
     * @param params
     * @return
     */
    int executeQuery(String jpsql, Object[] params);

    /**
     * 使用原始sql进行表操作
     * @param sql
     * @return
     */
    int executeBySql(String sql);

    /**
     * 使用原始sql进行表操作，带参数
     * @param sql
     * @param params
     * @return
     */
    int executeBySql(String sql, Object[] params);


    void setEntityClass(Class<T> entityClass);
}
