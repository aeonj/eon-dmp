package eon.hg.fap.common.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 平台扩展属性
 */
@ConfigurationProperties(prefix = "eon.hg.external")
@Data
public class ExternalProperties {
    //扩展的待初始化的实体对象包路径
    private String entity_packages;
    //慢请求处理时间阀值
    private long slow_req_millis = 10000;
    //记载用户请求信息的对象
    private String user_context_class = "eon.hg.fap.support.session.UserContext";
}
