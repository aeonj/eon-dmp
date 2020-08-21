package eon.hg.fap.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "eon.hg.external")
@Data
public class ExternalProperties {
    private String entity_packages;
}
