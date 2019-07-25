package eon.hg.fap.config;

import eon.hg.fap.security.access.EonAccessDecisionManager;
import eon.hg.fap.security.interceptor.SecureResourceFilterMetadataSource;
import eon.hg.fap.security.support.SecurityUserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnProperty(prefix = "eon.hg.security", name = "authType", havingValue = "token")
public class AuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SecurityUserSupport userService;

    @Autowired
    SecureResourceFilterMetadataSource secureResourceFilterMetadataSource;

    @Autowired
    EonAccessDecisionManager eonAccessDecisionManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean(name= BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 继承该方法注册自定义authenticationProvider，
     * 查看源码得知，auth.userDetailsService(userService).passwordEncoder(passwordEncoder());会产生新的DaoAuthenticationProvider
     *             HttpSecurity.authenticationProvider(authenticationProvider());会产生两个provider
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider(authenticationProvider());
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }


    //在这里配置哪些页面不需要认证
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/resources/**");
    }

    /**定义安全策略*/
    @Override
    public void configure(HttpSecurity http) throws Exception {
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
                .antMatchers("/login","/logout.do").permitAll()
                .and()
                .formLogin()
                .loginPage("/login.htm")   //登录页
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login.do")     //处理登录
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout.do"));
    }

}
