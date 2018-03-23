package project.k.w.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
public class MoaiOauthApplication {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		RestTemplate template =  new RestTemplate(factory);
		template.setErrorHandler(new MoaiResponseErrorHandler());
		return template;
	}

	public static void main(String[] args) {
		SpringApplication.run(MoaiOauthApplication.class, args);
	}

}
