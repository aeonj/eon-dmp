package eon.hg;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

@SpringBootApplication
@ServletComponentScan(basePackages = "eon.hg.*")
public class FapClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(FapClientApplication.class, args);
	}

	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverts() {
		//定义convert转换消息对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

		//添加FastJson的配置信息
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		PropertyFilter profilter = (object, name, value) -> {

			if (value instanceof HibernateProxy) {//hibernate代理对象
				return false;

			} else if (value instanceof PersistentCollection) {//实体关联集合一对多等
				PersistentCollection collection = (PersistentCollection) value;
				if (!collection.wasInitialized()) {
					return false;
				}
				Object val = collection.getValue();
				if (val == null) {
					return false;
				}
			}
			return true;
		};
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastJsonConfig.setSerializeFilters(profilter);

		//将配置设置给转换器并添加到HttpMessageConverter转换器列表中
		fastConverter.setFastJsonConfig(fastJsonConfig);

		return new HttpMessageConverters((HttpMessageConverter<?>) fastConverter);
	}

}
