package project.k.w.oauth.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;

public class Oauth2LogoutHandler implements LogoutHandler {

	private TokenStore tokenStore;
	
	public Oauth2LogoutHandler(TokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String token = request.getHeader("Authorization");
		Assert.hasText(token, "token must be set");
		if (isBearerToken(token)) {
			token = token.substring(7);
			OAuth2AccessToken existingAccessToken = tokenStore.readAccessToken(token);
			OAuth2RefreshToken refreshToken;
			if (existingAccessToken != null) {
				if (existingAccessToken.getRefreshToken() != null) {
					refreshToken = existingAccessToken.getRefreshToken();
					tokenStore.removeRefreshToken(refreshToken);
				}
				tokenStore.removeAccessToken(existingAccessToken);
			}
			return;
		} else {
			throw new BadClientCredentialsException();
		}

	}

	private boolean isBearerToken(String token) {
		return (token.startsWith("Bearer") || token.startsWith("bearer"));
	}

}
