package com.techkaksha.quizbuilder.config;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;

import java.util.Collection;
import java.util.Map;

public class CustomAuthenticationToken extends AbstractOAuth2TokenAuthenticationToken {
    public CustomAuthenticationToken(Jwt jwt){
        super(jwt);

    }

    @Override
    public Map<String, Object> getTokenAttributes() {
        return null;
    }

}
