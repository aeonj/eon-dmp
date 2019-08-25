package eon.hg.fap.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;


@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
	
	//@Bean
	public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    if (obj!=null) {
                        sb.append(obj.toString());
                    }
                }
                return sb.toString();
            }
        };
    }
}