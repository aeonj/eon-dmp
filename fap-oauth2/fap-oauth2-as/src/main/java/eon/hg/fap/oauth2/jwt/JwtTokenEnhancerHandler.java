package eon.hg.fap.oauth2.jwt;

import java.util.HashMap;

/**
 * 提供给外部实现调用的接口
 */
public interface JwtTokenEnhancerHandler {

    HashMap<String,Object> getInfoToToken();
}
