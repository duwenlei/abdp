package com.hiooih.base.auth;

import com.hiooih.core.jwt.entity.AuthenticationUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 登录验证
 *
 * @author duwenlei
 **/
@Component
@Slf4j
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        // 权限
        Set<GrantedAuthority> grants = new HashSet<>();
        grants.add(new SimpleGrantedAuthority("ADMIN"));

        AuthenticationUserEntity entity = new AuthenticationUserEntity();
        entity.setUserName(username);
        entity.setPasswordHash(password);
//        entity.setAuthorities(grants);

        return new UsernamePasswordAuthenticationToken(entity, password, grants);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
