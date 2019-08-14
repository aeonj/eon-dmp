package eon.hg.fap.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

public class EonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String credentialsCharset = "UTF-8";

    private final ClientDetailsService clientDetailsService;

    private final AuthorizationServerTokenServices authorizationServerTokenServices;

    private final PasswordEncoder passwordEncode;

    private final ObjectMapper objectMapper;

    @Autowired
    public EonAuthenticationSuccessHandler(ClientDetailsService clientDetailsService,
                                           AuthorizationServerTokenServices authorizationServerTokenServices, PasswordEncoder passwordEncode,
                                           ObjectMapper objectMapper) {
        this.clientDetailsService = clientDetailsService;
        this.authorizationServerTokenServices = authorizationServerTokenServices;
        this.passwordEncode = passwordEncode;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("login successfully.");
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("There is not client information.");
        }

        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        if (logger.isDebugEnabled()) {
            logger.debug("Client Id		: {}", clientId);
            logger.debug("Client Secret	: {}", clientSecret);
        }

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("Client information was not found: " + clientId);
        }
        if (!passwordEncode.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("Client secret was not matched: " + clientId);
        }

        @SuppressWarnings("unchecked")
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_SORTED_MAP, clientId, clientDetails.getScope(),
                "custom");

        // create a OAuth2Request
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        // create a OAuth2Authentication
        OAuth2Authentication oAuth2Authorization = new OAuth2Authentication(oAuth2Request, authentication);

        // produce a token
        OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authorization);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");

        // write date as timestamp
        objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        response.getWriter().write(objectMapper.writeValueAsString(oAuth2AccessToken));
    }

    /**
     * Decodes the header into a username and password.
     *
     * @throws BadCredentialsException
     *             if the Basic header is not present or is not valid Base64
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, credentialsCharset);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }
}
