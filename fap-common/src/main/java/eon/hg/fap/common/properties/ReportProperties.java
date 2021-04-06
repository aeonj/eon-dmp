package eon.hg.fap.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "eon.hg.report")
@Data
public class ReportProperties {
    private String server_url = "";
}
