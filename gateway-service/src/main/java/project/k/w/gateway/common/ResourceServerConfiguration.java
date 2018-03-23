package project.k.w.gateway.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	/**
	 * Configure security to allow access to the /oauth/token endpoint.
	 */
	@Override 
    public void configure(HttpSecurity http) throws Exception {
		   http
//		   .requestMatchers()
//		   .anyRequest()
//           .and()
           .authorizeRequests()
           .antMatchers("/*/oauth/token").permitAll()
           .antMatchers(HttpMethod.POST, "/*/v1/accounts").permitAll()
           .anyRequest().authenticated()
         ;
    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
         resources.resourceId("project.k.w").stateless(true);
    }
}
