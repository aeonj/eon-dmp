package eon.hg.fap.core.jpa.support;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 自定义JPA资源库工厂，指向自定义的实体数据操作实现
 * @param <T>
 * @param <I>
 */
public class BaseRepositoryFactory<T, I extends Serializable>
        extends JpaRepositoryFactory {
    /**
     * Creates a new {@link JpaRepositoryFactory}.
     *
     * @param entityManager must not be {@literal null}
     */
    public BaseRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
    }

    //设置具体的实现类的class
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return SimpleBaseRepository.class;
    }
}
