package com.hiooih.base.auth.handler;

import com.hiooih.base.response.result.ApiCode;
import com.hiooih.base.response.result.ApiResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 没有用户登录情况，处理
 *
 * @author duwenlei
 **/
@Component
public class UserAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(401);
        ApiResult.response(response, ApiCode.FORBIDDEN_RESOURCE);
    }
}
