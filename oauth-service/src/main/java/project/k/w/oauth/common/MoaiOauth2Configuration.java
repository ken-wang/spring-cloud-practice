package project.k.w.oauth.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;

import project.k.w.oauth.controller.OtherTokenEndpoint;
import project.k.w.oauth.security.granter.DeviceProxyTokenGranter;
import project.k.w.oauth.security.provider.Oauth2AuthenticationProvider;

public abstract class MoaiOauth2Configuration extends AuthorizationServerConfigurerAdapter {

	private static String RESOURCE_ID = "aristotle";
	
	@Autowired
	private AuthenticationManager auth;
	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private AuthorizationServerEndpointsConfiguration endpointsConfigurer;
	
	public abstract TokenStore tokenStore();
	
	@Bean
	public OtherTokenEndpoint otherTokenEndpoint() throws Exception {
		
		OtherTokenEndpoint tokenEndpoint = new OtherTokenEndpoint();
		tokenEndpoint.setClientDetailsService(clientDetailsService);
		tokenEndpoint.setProviderExceptionHandler(endpointsConfigurer.getEndpointsConfigurer().getExceptionTranslator());
		tokenEndpoint.setTokenGranter(endpointsConfigurer.getEndpointsConfigurer().getTokenGranter());
		tokenEndpoint.setOAuth2RequestFactory(endpointsConfigurer.getEndpointsConfigurer().getOAuth2RequestFactory());
		return tokenEndpoint;
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		/*
		 *  https://stackoverflow.com/questions/45767147/how-to-use-authorizationserversecurityconfigurer
		 *  
		 */
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients();
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenGranter(tokenGranter(endpoints))
			.tokenStore(tokenStore())
//			.tokenServices(tokenServices)
//			.accessTokenConverter(accessTokenConverter)
			.authenticationManager(auth);
	}
	
	private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {     

        List<TokenGranter> granters = new ArrayList<TokenGranter>();
        granters.add(new AuthorizationCodeTokenGranter(endpoints.getTokenServices(), endpoints.getAuthorizationCodeServices(),
                endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        granters.add(new RefreshTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        granters.add(new ImplicitTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        granters.add(new ClientCredentialsTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        granters.add(new ResourceOwnerPasswordTokenGranter(auth, endpoints.getTokenServices(),
                endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        //Custom Token Granter
        granters.add(new DeviceProxyTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));

        return new CompositeTokenGranter(granters);
    }


	/**
	 * Setup the client application which attempts to get access to user's account
	 * after user permission.
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("aristotle_gateway_client").resourceIds(RESOURCE_ID)
				.scopes("all") // TODO
				.authorities("client")
				.secret("123456")
				.and()
				.withClient("aristotle_app_client").resourceIds(RESOURCE_ID)
				.authorizedGrantTypes("password", "refresh_token")
				.scopes("all") // TODO
				.authorities("client")
				.secret("123456")
				.and()
				.withClient("aristotle_galileo_client").resourceIds(RESOURCE_ID)
				.authorizedGrantTypes("device_proxy", "refresh_token")
				.scopes("all") // TODO
				.authorities("client")
				.secret("123456");
	}

	/**
	 * Configure the {@link AuthenticationManagerBuilder} with initial configuration
	 * to setup users.
	 * 
	 * @author anilallewar
	 *
	 */
	@Configuration
	@Order(Ordered.LOWEST_PRECEDENCE - 20)
	protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

		@Autowired
		private Oauth2AuthenticationProvider provider;
		
		@Override
		public void init(AuthenticationManagerBuilder builder) throws Exception {
			builder.authenticationProvider(provider);
		}
		
	}

}
