package eon.hg.fap.config;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import eon.hg.fap.common.tools.json.FastJsonPropertyFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnClass({FastJsonHttpMessageConverter.class})
@ConditionalOnProperty(
        name = {"spring.http.converters.preferred-json-mapper"},
        havingValue = "fastjson"
)
public class JsonConvertsConfig {

    public HttpMessageConverter<String> stringHttpMessageConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Bean
    @ConditionalOnMissingBean({FastJsonHttpMessageConverter.class})
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverts() {

        //定义convert转换消息对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverterExtension();

        //添加FastJson的配置信息
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        PropertyFilter proFilter = new FastJsonPropertyFilter();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,SerializerFeature.DisableCircularReferenceDetect);
        fastJsonConfig.setSerializeFilters(proFilter);
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));

        //将配置设置给转换器并添加到HttpMessageConverter转换器列表中
        fastConverter.setFastJsonConfig(fastJsonConfig);

        return fastConverter;
    }

    public class FastJsonHttpMessageConverterExtension extends FastJsonHttpMessageConverter {
        FastJsonHttpMessageConverterExtension() {
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
            setSupportedMediaTypes(mediaTypes);
        }

        @Override
        public void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException {
            if (obj instanceof String) {
                Charset charset = Charset.forName("UTF-8");
                StreamUtils.copy((String) obj, charset, outputMessage.getBody());
            } else {
                super.writeInternal(obj,outputMessage);
            }
        }

    }
}
