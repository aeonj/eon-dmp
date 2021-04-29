package eon.hg.fap.core.beans;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 数据库用户名，用于sql语句跨库使用，vcf中有用到
 * @author eonook
 */
@Slf4j
@Component
public class DbParams implements ApplicationContextAware {
    private static String primaryDbUser;
    private static String secondaryDbUser;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            if (applicationContext.containsBean("primaryDataSource")) {
                DataSource primaryDataSource = applicationContext.getBean("primaryDataSource", DataSource.class);
                primaryDbUser = primaryDataSource.unwrap(HikariDataSource.class).getUsername();
            }
            if (applicationContext.containsBean("secondaryDataSource")) {
                DataSource secondaryDataSource = applicationContext.getBean("secondaryDataSource", DataSource.class);
                secondaryDbUser = secondaryDataSource.unwrap(HikariDataSource.class).getUsername();
            }
        } catch (SQLException e) {
            log.info("数据源转换错误，HikariDataSource不支持");
        }
    }

    public static String getPrimaryDbUser() {
        return primaryDbUser;
    }

    public static String getSecondaryDbUser() {
        return secondaryDbUser;
    }

}
