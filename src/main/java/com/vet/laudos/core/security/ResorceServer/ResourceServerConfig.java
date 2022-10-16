package com.vet.laudos.core.security.ResorceServer;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	
	//System.out.print(passwordEncoder().encode("123"));
	//$2a$10$GOP888TBozG5knqJp1iGDuahz6WduZlcWJ3Gw.9vX7AVGFjoooWTq
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin()
			.and()
			.authorizeHttpRequests()
				.antMatchers("/oauth/**").authenticated()
			.and()
				.cors()
			.and()
				.oauth2ResourceServer().jwt();
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
		var secretKey = new SecretKeySpec("dgasdgsahgdhbvhjvauyigv41as4846x5c8484dsc844".getBytes(), "HmacSHA256");
		return NimbusJwtDecoder.withSecretKey(secretKey).build();
	}

}
