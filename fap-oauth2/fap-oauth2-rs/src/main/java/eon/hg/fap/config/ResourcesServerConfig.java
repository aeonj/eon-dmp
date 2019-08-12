package eon.hg.fap.config;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.security.access.EonAccessDecisionManager;
import eon.hg.fap.security.interceptor.SecureResourceFilterMetadataSource;
import eon.hg.fap.security.support.SecurityUserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableResourceServer
@ConditionalOnProperty(prefix = "eon.hg.security", name = "authType", havingValue = "token")
public class ResourcesServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${eon.hg.security.oauth2.resourceId}")
    private String resourceId;

    @Autowired
    SecurityUserSupport userService;

    @Autowired
    SecureResourceFilterMetadataSource secureResourceFilterMetadataSource;

    @Autowired
    EonAccessDecisionManager eonAccessDecisionManager;

    @Autowired(required = false)
    RemoteTokenServices remoteTokenServices;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceId);
        if (remoteTokenServices!=null) {
            DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
            DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
            userAuthenticationConverter.setUserDetailsService(userService);
            accessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
            remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
        }
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        Globals.AUTH_TYPE = "token";
        http.userDetailsService(userService);
        http.headers().frameOptions().sameOrigin();
        http
                .authorizeRequests()     //配置安全策略
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(secureResourceFilterMetadataSource);
                        object.setAccessDecisionManager(eonAccessDecisionManager);
                        return object;
                    }
                })
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable();
    }
}
