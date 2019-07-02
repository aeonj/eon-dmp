package eon.hg.fap.config;

import com.caucho.hessian.client.HessianProxyFactory;
import eon.hg.fap.hessian.ibs.ILogin;
import eon.hg.fap.hessian.ibs.IPreLogin;
import eon.hg.fap.hessian.ibs.ISysBillType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

@Configuration
public class HessianBeanConfig {

    final HessianProxyFactory factory = new HessianProxyFactory();

    final String url = "http://localhost:8080/vcf/remote/";

//    @Bean
//    public HessianProxyFactoryBean cxj() {
//        HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
//        factory.setServiceUrl(url+"sysBillTypeHttpInvoker");
//        factory.setServiceInterface(ISysBillType.class);
//        return factory;
//    }

    @Bean
    public ISysBillType sysBillType() throws MalformedURLException {
        return (ISysBillType) factory.create(ISysBillType.class,url+"sysBillTypeHttpInvoker");
    }

    @Bean
    public IPreLogin preLogin() throws MalformedURLException {
        return (IPreLogin) factory.create(IPreLogin.class,url+"preLoginHttpInvoker");
    }

    @Bean
    public ILogin login() throws MalformedURLException {
        return (ILogin) factory.create(ILogin.class, url+"loginHttpInvoker");
    }

}
