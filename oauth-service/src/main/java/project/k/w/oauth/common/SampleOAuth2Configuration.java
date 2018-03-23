package project.k.w.oauth.common;
//package com.babytree.fuhu.moai.tahai.oauth.common;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
//import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//
//import com.babytree.fuhu.moai.tahai.oauth.provider.MoaiAuthenticationProvider;
//
////@Configuration
////@EnableAuthorizationServer
//public class SampleOAuth2Configuration extends AuthorizationServerConfigurerAdapter {
//
//	@Autowired
//	private AuthenticationManager auth;
//
//	@Autowired
//	private DataSource dataSource;
//
//	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//	/**
//	 * The OAuth2 tokens are defined in the datasource defined in the
//	 * <code>auth-server.yml</code> file stored in the Spring Cloud config
//	 * github repository.
//	 * 
//	 * @return
//	 */
//	@Bean
//	public JdbcTokenStore tokenStore() {
//		return new JdbcTokenStore(dataSource);
//	}
//
//	@Bean
//	protected AuthorizationCodeServices authorizationCodeServices() {
//		return new JdbcAuthorizationCodeServices(dataSource);
//	}
//
//	@Override
//	public void configure(AuthorizationServerSecurityConfigurer security)
//			throws Exception {
//		security.passwordEncoder(passwordEncoder);
//	}
//
//	/**
//	 * We set our authorization storage feature specifying that we would use the
//	 * JDBC store for token and authorization code storage.<br>
//	 * <br>
//	 * 
//	 * We also attach the {@link AuthenticationManager} so that password grants
//	 * can be processed.
//	 */
//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
//			throws Exception {
//		endpoints.authorizationCodeServices(authorizationCodeServices())
//				.authenticationManager(auth)
//				.tokenStore(tokenStore())
//				.approvalStoreDisabled();
//	}
//
//	/**
//	 * Setup the client application which attempts to get access to user's
//	 * account after user permission.
//	 */
//	@Override
//	public void configure(ClientDetailsServiceConfigurer clients)
//			throws Exception {
//	
//		clients.jdbc(dataSource)
//				.passwordEncoder(passwordEncoder)
//				.withClient("client")
//				.authorizedGrantTypes("authorization_code", "client_credentials", 
//						"refresh_token","password", "implicit")
//				.authorities("ROLE_CLIENT")
//				.resourceIds("apis")
//				.scopes("read")
//				.secret("secret")
//				.accessTokenValiditySeconds(300);
//	
//	}
//	
//	/**
//	 * Configure the {@link AuthenticationManagerBuilder} with initial
//	 * configuration to setup users.
//	 * 
//	 * @author anilallewar
//	 *
//	 */
////	@Configuration
//	@Order(Ordered.LOWEST_PRECEDENCE - 20)
//	protected static class AuthenticationManagerConfiguration extends
//			GlobalAuthenticationConfigurerAdapter {
//
//		@Autowired
//		private MoaiAuthenticationProvider provider;
//
//		/**
//		 * Setup 2 users with different roles
//		 */
//		@Override
//		public void init(AuthenticationManagerBuilder auth) throws Exception {
//			auth.authenticationProvider(provider);
////			auth.jdbcAuthentication().dataSource(dataSource).withUser("dave")
////					.password("secret").roles("USER");
////			auth.jdbcAuthentication().dataSource(dataSource).withUser("anil")
////					.password("password").roles("ADMIN");
//		}
//	}
//
//
//}
