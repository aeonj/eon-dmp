package eon.hg.fap.core.jpa.support;

import eon.hg.fap.core.jpa.GenericRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class BaseRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {

    private EntityPathResolver resolver;

    public BaseRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Autowired
    public void setResolver(ObjectProvider<EntityPathResolver> resolver) {
        this.resolver = resolver.getIfAvailable(() -> SimpleEntityPathResolver.INSTANCE);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        if (GenericRepository.class.isAssignableFrom(getObjectType())) {
            GenericRepositoryFactory repositoryFactory = new GenericRepositoryFactory(em);
            return repositoryFactory;
        } else {
            BaseRepositoryFactory repositoryFactory = new BaseRepositoryFactory(em);
            repositoryFactory.setEntityPathResolver(this.resolver);
            return repositoryFactory;
        }
    }

}
