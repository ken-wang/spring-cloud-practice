package project.k.w.oauth.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
@Profile("local")
public class LocalTokenStoreConfiguration extends MoaiOauth2Configuration {

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}
}
