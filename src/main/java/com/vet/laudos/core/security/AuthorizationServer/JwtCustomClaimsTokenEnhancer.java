package com.vet.laudos.core.security.AuthorizationServer;

import java.util.HashMap;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer{

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		var authUser = (AuthUser) authentication.getPrincipal();
		
		var info = new HashMap<String, Object>();
		
		info.put("user_id", authUser.getUserId());
		info.put("user_first_name", authUser.getUserName());
		
		var oauth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
		oauth2AccessToken.setAdditionalInformation(info);
		
		return accessToken;
	}

}
