package com.hiooih.base.auth.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hiooih.AbdpApplication;
import com.hiooih.base.exception.AbdpException;
import com.hiooih.base.response.result.ApiCode;
import com.hiooih.base.response.result.ApiResult;
import com.hiooih.core.jwt.AbdpToken;
import com.hiooih.core.jwt.JwtConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.Objects;

/**
 * 验证 Token
 *
 * @author duwenlei
 **/
@Slf4j
public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 手动注入 Bean
        JwtConfig jwtConfig = (JwtConfig) AbdpApplication.getBean("jwtConfig", JwtConfig.class);
        AbdpToken abdpToken = (AbdpToken) AbdpApplication.getBean("abdpToken", AbdpToken.class);

        String authenticationToken = request.getHeader(jwtConfig.getTokenHeader());
        String token = authenticationToken.replace(jwtConfig.getTokenPrefix(), "");

        try {
            Objects.requireNonNull(authenticationToken, ApiCode.PARAM_LACK.getMsg());
            DecodedJWT verify = abdpToken.verify(token);
            Map<String, Object> userInfo = verify.getClaim("userInfo").asMap();
            log.info(userInfo.toString());
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Token 验证异常，原因：{}", e.getMessage(), e);
            ApiResult.response(response, ApiCode.JWT_AUTH_FILTER_FAILURE);
        }

    }
}
