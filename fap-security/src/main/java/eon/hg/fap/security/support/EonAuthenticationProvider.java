package eon.hg.fap.security.support;

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
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}

		String presentedPassword = authentication.getCredentials().toString();

		if (presentedPassword.indexOf(Globals.THIRD_ACCOUNT_LOGIN) >= 0) {
			presentedPassword = presentedPassword.substring(Globals.THIRD_ACCOUNT_LOGIN.length());
			if (!presentedPassword.equals(userDetails.getPassword())) {
				throw new BadCredentialsException(this.messages
						.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			}
		} else {
			if (!getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
				logger.debug("Authentication failed: password does not match stored value");

				throw new BadCredentialsException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.badCredentials",
						"Bad credentials"));
			}
		}
	}


}
