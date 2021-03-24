package me.helpeachother.springsecurity.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * <pre>
 * Class Name : CustomPersistentTokenBasedRememberMeServices
 * Description : Customized PersistentTokenBasedRememberMeServices
 *
 *
 *  Modification Information
 *  Modify Date 	Modifier		Comment
 *  -----------------------------------------------
 *  2021.03.24      swkim  		     New
 *
 * </pre>
 *
 * @author swkim
 * @since 2021.03.24
 */

public class CustomPersistentTokenBasedRememberMeServices extends PersistentTokenBasedRememberMeServices {
	
	private PersistentTokenRepository tokenRepository = new InMemoryTokenRepositoryImpl();
	
	public CustomPersistentTokenBasedRememberMeServices(String key,
                                                        UserDetailsService userDetailsService,
                                                        PersistentTokenRepository tokenRepository) {
		super(key, userDetailsService, tokenRepository);
		this.tokenRepository = tokenRepository;
	}
	
	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response){
		
		if (cookieTokens.length != 2) {
			throw new InvalidCookieException("Cookie token did not contain " + 2
					+ " tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
		}

		final String presentedSeries = cookieTokens[0];
		final String presentedToken = cookieTokens[1];

		PersistentRememberMeToken token = tokenRepository
				.getTokenForSeries(presentedSeries);

		if (token == null) {
			// No series match, so we can't authenticate using this cookie
			throw new RememberMeAuthenticationException(
					"No persistent token found for series id: " + presentedSeries);
		}

		// We have a match for this user/series combination
		if (!presentedToken.equals(token.getTokenValue())) {
			// Token doesn't match series value. Delete all logins for this user and throw
			// an exception to warn them.
			tokenRepository.removeUserTokens(token.getUsername());

			throw new CookieTheftException(
					messages.getMessage(
							"PersistentTokenBasedRememberMeServices.cookieStolen",
							"Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack."));
		}

		if (token.getDate().getTime() + getTokenValiditySeconds() * 1000L < System
				.currentTimeMillis()) {
			throw new RememberMeAuthenticationException("Remember-me login has expired");
		}

		return getUserDetailsService().loadUserByUsername(token.getUsername());
	}
}
