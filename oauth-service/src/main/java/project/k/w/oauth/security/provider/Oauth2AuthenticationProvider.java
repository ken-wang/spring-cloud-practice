package project.k.w.oauth.security.provider;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import project.k.w.oauth.security.AccountAuthenticationToken;
import project.k.w.oauth.view.json.AccountValidationRequest;
import project.k.w.oauth.view.json.AccountValidationResult;
import project.k.w.validator.EmailValidator;

@Component
public class Oauth2AuthenticationProvider implements AuthenticationProvider {

	public static Logger logger =  LoggerFactory.getLogger(Oauth2AuthenticationProvider.class);
	
	private EmailValidator emailValidator = new EmailValidator();
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${account.service.host}")
	private String host;

	@Value("${account.service.validation.uri}")
	private String validationURI;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String login = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		grantedAuths.add(new SimpleGrantedAuthority("USER"));
		
		AccountValidationRequest request = createAccountValidationRequest(login, password);
		try {
			ResponseEntity<AccountValidationResult> entity = restTemplate.postForEntity(host + validationURI, request, AccountValidationResult.class);
			return new AccountAuthenticationToken(entity.getBody(), password, grantedAuths);
		} catch (HttpClientErrorException e) {
			logger.error(e.getMessage(), e);
			throw new UnauthorizedUserException(e.getResponseBodyAsString(), e);
		} catch (HttpServerErrorException e) {
			logger.error(e.getMessage(), e);
			throw new InternalAuthenticationServiceException(e.getResponseBodyAsString(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new InternalAuthenticationServiceException(e.getMessage(), e); 
		}
	}
	
	private AccountValidationRequest createAccountValidationRequest(String login, String password) {
		
		if(emailValidator.isValid(login)) {
			return new AccountValidationRequest(login, null, password);
		} else {
			return new AccountValidationRequest(null, login, password);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
