package eon.hg.fap.flow.config;

import eon.hg.fap.flow.console.FlowServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FlowServletConfig {
    @Bean
    public ServletRegistrationBean servletRegistration() {
        return new ServletRegistrationBean(new FlowServlet(), "/flow/*");
    }

}
