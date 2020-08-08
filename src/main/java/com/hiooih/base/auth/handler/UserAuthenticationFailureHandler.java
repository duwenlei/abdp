package com.hiooih.base.auth.handler;

import com.hiooih.base.response.result.ApiCode;
import com.hiooih.base.response.result.ApiResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 登录失败处理类
 *
 * @author duwenlei
 **/
@Component
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof UsernameNotFoundException) {
            response.getOutputStream().write(ApiResult.failure(ApiCode.USERNAME_NOT_FOUND).toString().getBytes(StandardCharsets.UTF_8));
        }
        response.getOutputStream().write(ApiResult.failure().toString().getBytes(StandardCharsets.UTF_8));
    }
}
