package eon.hg.fap.config.tokenstore;

import cn.hutool.core.convert.Convert;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.security.support.SecurityUserSupport;
import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@ConditionalOnProperty(
        prefix = "eon.hg.security.oauth2",
        name = "storeType",
        havingValue = "jwt",
        matchIfMissing = true)
public class JwtTokenStoreConfig {

    @Autowired
    SecurityUserSupport userService;

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //设置jwt的登录对象转换策略，直接从token里面直接取
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter() {
            @Override
            public Authentication extractAuthentication(Map<String, ?> map) {
                if (map.containsKey("uom")) {
                    Map mapUser = (Map)map.get("uom");
                    Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>((Collection) mapUser.get("authorities"));
                    String userid = Convert.toStr(mapUser.get("userid"),"-1");
                    String username = Convert.toStr(mapUser.get("username"),"");
                    String password = Convert.toStr(mapUser.get("userid"));
                    OnlineUser user = new OnlineUser(userid,username,password,grantedAuthoritySet);
                    Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
                    Object principal = user;
                    return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
                } else {
                    return super.extractAuthentication(map);
                }
            }
        };
        accessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        converter.setAccessTokenConverter(accessTokenConverter);

        Resource resource = new ClassPathResource("jwt.txt");
        String publicKey;
        try {
            publicKey = IOUtils.toString(resource.getInputStream());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }
}
