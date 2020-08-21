package eon.hg.fap.config;

import eon.hg.fap.common.properties.ExternalProperties;
import eon.hg.fap.common.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({SecurityProperties.class, ExternalProperties.class})
public class PropertiesConfig {
}
