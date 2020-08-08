package com.hiooih.base.auth;

import com.hiooih.core.jwt.entity.AuthenticationUserEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 登录验证
 *
 * @author duwenlei
 **/
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationUserEntity userEntity = (AuthenticationUserEntity) authentication.getPrincipal();
        if (userEntity.getUserName() != null) {

        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
