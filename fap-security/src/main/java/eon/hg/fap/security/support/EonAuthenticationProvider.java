package eon.hg.fap.security.support;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import eon.hg.fap.core.constant.Globals;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 重写AbstractUserDetailsAuthenticationProvider，用来控制第三方账号登陆
 * ，第三方账号登录可以使用MD5后密码进行登录 ,MD5密码登录使用Globals.THIRD_ACCOUNT_LOGIN作为前缀标识，系统自动不进行处理
 * 
 * @author AEON
 * 
 */
public class EonAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractSecurityInterceptor.authenticationNotFound",
					"未能获取用户授权令牌"));
		}
		if (authentication.getCredentials() instanceof SsoCredential) {
			SsoCredential credential = (SsoCredential) authentication.getCredentials();
			if (DateUtil.between(DateUtil.parse(credential.getTimestamp()),DateUtil.date(), DateUnit.SECOND)>SsoCredential.getExpired()) {
				throw new BadCredentialsException(this.messages
						.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "用户授权认证已过期"));
			}
			String sign = SecureUtil.md5(userDetails.getUsername()+credential.getTimestamp()+SsoCredential.getCertKey());
			if (!sign.equals(credential.getSign())) {
				throw new BadCredentialsException(this.messages
						.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "用户登录验证失败"));
			}
		} else {
			String presentedPassword = authentication.getCredentials().toString();

			if (presentedPassword.indexOf(Globals.THIRD_ACCOUNT_LOGIN) >= 0) {
				presentedPassword = presentedPassword.substring(Globals.THIRD_ACCOUNT_LOGIN.length());
				if (!presentedPassword.equals(userDetails.getPassword())) {
					throw new BadCredentialsException(this.messages
							.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "用户登录验证请求未通过"));
				}
			} else {
				if (!getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
					logger.debug("Authentication failed: password does not match stored value");

					throw new BadCredentialsException(messages.getMessage(
							"AbstractUserDetailsAuthenticationProvider.badCredentials",
							"用户名或密码错误"));
				}
			}
		}
	}


}
