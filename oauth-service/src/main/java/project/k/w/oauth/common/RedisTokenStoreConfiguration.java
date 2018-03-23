package project.k.w.oauth.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
@Profile("prod")
public class RedisTokenStoreConfiguration extends MoaiOauth2Configuration {

	@Override
	public TokenStore tokenStore() {
		return tokenStore(null);
	}
	
	@Bean
	public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
		return new RedisTokenStore(redisConnectionFactory);
	}
}
