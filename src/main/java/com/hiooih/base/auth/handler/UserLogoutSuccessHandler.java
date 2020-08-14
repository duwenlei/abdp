package com.hiooih.base.auth.handler;

import com.hiooih.base.response.result.ApiCode;
import com.hiooih.base.response.result.ApiResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author duwenlei
 **/
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ApiResult.response(response, ApiCode.USER_LOGOUT_SUCCESS);
        // TODO 清除登录 Token
        SecurityContextHolder.clearContext();
    }
}
