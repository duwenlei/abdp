package com.hiooih.base.auth.handler;

import com.hiooih.abdp.system.entity.SysUser;
import com.hiooih.abdp.system.service.ISysUserService;
import com.hiooih.base.response.result.ApiCode;
import com.hiooih.base.response.result.ApiResult;
import com.hiooih.core.jwt.AbdpToken;
import com.hiooih.core.jwt.entity.AuthenticationUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 登录成功
 *
 * @author duwenlei
 **/
@Component
@Slf4j
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AbdpToken abdpToken;

    @Autowired
    @Qualifier("sysUserService")
    private ISysUserService sysUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthenticationUserEntity principal = (AuthenticationUserEntity) authentication.getPrincipal();
        try {
            String token = abdpToken.create(principal);
            Map<String, String> respData = new HashMap<>(1);
            respData.put("token", token);
            ApiResult.response(response, ApiCode.LOGIN_SUCCESS, respData);
        } catch (UnrecoverableKeyException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("登录成功，生成 Token 失败，原因：{}", e.getMessage(), e);
            ApiResult.response(response, ApiCode.FAILURE);
        }
    }
}
