package eon.hg.fap.flow.config;

import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.flow.env.EnvironmentProvider;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

@Configuration
@ImportResource(locations = {"classpath:config/flow-context.xml"})
public class EnvironmentProviderImpl implements EnvironmentProvider {

    @Resource(name = "entityManagerPrimary")
    private EntityManager entityManager;

    @Resource(name = "transactionManagerPrimary")
    private PlatformTransactionManager transactionManager;

    @Override
    public SessionFactory getSessionFactory() {
        return entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public PlatformTransactionManager getPlatformTransactionManager() {
        return new JpaTransactionManager(entityManager.getEntityManagerFactory());
    }

    @Override
    public String getLoginUser() {
        OnlineUser user = SecurityUserHolder.getOnlineUser();
        return user == null ? null : user.getUserid();
    }

    @Override
    public String getCategoryId() {
        return null;
    }
}
