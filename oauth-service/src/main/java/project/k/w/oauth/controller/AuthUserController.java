package project.k.w.oauth.controller;

import java.security.Principal;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthUserController {
	
	@RequestMapping("/me")
	public @ResponseBody Object getPrincipal(Principal principal) {
		return principal;
	}
	
	
	@RequestMapping("/user/info")
	public @ResponseBody Object getCurrentLoggedInUser(Principal principal) {
		
		OAuth2Authentication authentication = (OAuth2Authentication) principal;
		return authentication.getUserAuthentication().getPrincipal();
		
	}
	

}
