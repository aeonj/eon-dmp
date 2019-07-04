package eon.hg.fap.config;

import eon.hg.fap.hessian.ibs.IDemoHessian;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

@Configuration
public class HessianBeanConfig {


    final String url = "http://localhost:8080/vcf/remote/";

    @Bean
    public HessianProxyFactoryBean cxj() {
        HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
        factory.setServiceUrl(url+"sysBillTypeHttpInvoker");
        factory.setServiceInterface(IDemoHessian.class);
        return factory;
    }

}
