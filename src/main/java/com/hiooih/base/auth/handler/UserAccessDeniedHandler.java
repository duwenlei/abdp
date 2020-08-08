package com.hiooih.base.auth.handler;

import com.hiooih.base.response.result.ApiCode;
import com.hiooih.base.response.result.ApiResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Handles an access denied failure.
 *
 * @author duwenlei
 **/
@Component
public class UserAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(403);
        response.getOutputStream().write(ApiResult.failure(ApiCode.FORBIDDEN_RESOURCE).toString().getBytes(StandardCharsets.UTF_8));
    }
}
