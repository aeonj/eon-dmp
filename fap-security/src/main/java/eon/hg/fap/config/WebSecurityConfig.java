package eon.hg.fap.config;

import eon.hg.fap.security.access.EonAccessDecisionManager;
import eon.hg.fap.security.access.EonAccessDeniedHandler;
import eon.hg.fap.security.interceptor.SecureResourceFilterMetadataSource;
import eon.hg.fap.security.support.EonAuthenticationProvider;
import eon.hg.fap.security.support.SecurityUserSupport;
import eon.hg.fap.security.web.DnsSecurityExceptionFilter;
import eon.hg.fap.security.web.LoginAuthenticationFilter;
import eon.hg.fap.security.web.LoginUrlEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnProperty(prefix = "eon.hg.security", name = "authType", havingValue = "session")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SecurityUserSupport userService;

    @Autowired
    SecureResourceFilterMetadataSource secureResourceFilterMetadataSource;

    @Autowired
    EonAccessDecisionManager eonAccessDecisionManager;

    @Autowired
    EonAccessDeniedHandler dnsAccessDeniedHandler;

    @Autowired
    LoginUrlEntryPoint loginUrlEntryPoint;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        EonAuthenticationProvider authenticationProvider = new EonAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        //redis可设置UserCache缓存
        //authenticationProvider.setUserCache();
        return authenticationProvider;
    }

    @Bean(name= BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UsernamePasswordAuthenticationFilter eonLoginAuthenticationFilter() throws Exception {
        return new LoginAuthenticationFilter(authenticationManagerBean());
    }

    @Bean
    public DnsSecurityExceptionFilter dnsSecurityExceptionFilter() {
        return new DnsSecurityExceptionFilter(loginUrlEntryPoint);
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
    }


    //在这里配置哪些页面不需要认证
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/","/webjars/**", "/login.htm*","/domain_error.htm*","/manage/*tag.htm*","/resource/**","/extjs6.2/**");
    }

    /**定义安全策略*/
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
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
                .formLogin()
                .loginPage("/login.htm")   //登录页
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .loginProcessingUrl("/iaeon_login.htm")     //处理登录
                .successForwardUrl("/login_success.htm")     //首页
                .failureUrl("/login_error.htm")
                //.and().rememberMe().key("aeon.com")
                .and()
                .logout()
                .permitAll()
                .logoutUrl("/iaeon_logout.htm")
                .logoutSuccessUrl("/logout_success.htm")
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                //.authenticationEntryPoint(loginUrlEntryPoint)
                .accessDeniedHandler(dnsAccessDeniedHandler);
        http.addFilterAt(eonLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //http.addFilterAfter(dnsSecurityExceptionFilter(), ExceptionTranslationFilter.class);
        //http.authenticationProvider(authenticationProvider());
    }

}
