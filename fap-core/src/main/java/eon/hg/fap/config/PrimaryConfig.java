package eon.hg.fap.config;

import eon.hg.fap.core.jpa.support.BaseRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryPrimary",
        transactionManagerRef="transactionManagerPrimary",
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class,
        basePackages= {"eon.hg.*.db.dao.primary"})//设置dao（repo）所在位置
public class PrimaryConfig {

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;

    @Autowired
    @Qualifier("vendorProperties")
    private Map<String, Object> vendorProperties;

    @Bean(name = "entityManagerFactoryPrimary")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary (EntityManagerFactoryBuilder builder) {
        //识别缺省的JDBC驱动类型(aeonDao)
        try {
            String dbString = primaryDataSource.getConnection().getMetaData().getDatabaseProductName().toLowerCase();
            if (dbString.indexOf("ora") > -1) {
                System.setProperty("aeonDao.db", "oracle");
            } else if (dbString.indexOf("mysql") > -1) {
                System.setProperty("aeonDao.db", "mysql");
            } else if (dbString.indexOf("microsoft") > -1) {
                System.setProperty("aeonDao.db", "sqlserver");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder
                .dataSource(primaryDataSource)
                .properties(vendorProperties)
                .packages("eon.hg.*.db.model.primary") //设置实体类所在位置
                .persistenceUnit("primaryPersistenceUnit")
                .build();
    }

    @Bean(name = "entityManagerPrimary")
    @Primary
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
    }

    @Bean(name = "transactionManagerPrimary")
    @Primary
    PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }

}
