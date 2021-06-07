package eon.hg.fap.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "eon.hg.security")
@Data
public class SecurityProperties {
    private String login_url = "/user/login.htm";
    private String index_url = "/index.htm";
    private String welcome_url = "/manage/workbench.htm";
}
