package eon.hg.fap.config.tokenstore;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "eon.hg.security.oauth2", name = "storeType", havingValue = "jdbc")
public class JdbcTokenStoreConfig {

    @Resource(name = "primaryDataSource")
    DataSource oauthDataSource;

    @Bean
    public TokenStore jdbcTokenStore(){
        return new JdbcTokenStore(oauthDataSource);
    }

}
