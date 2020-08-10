package com.hiooih.base.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 注解验证
 *
 * @author duwenlei
 **/
@Component
@Slf4j
public class UserPermissionEvaluator implements PermissionEvaluator {
    /**
     * 验证
     *
     * @param authentication 身份
     * @param targetUrl      目标路径
     * @param permission     权限
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        log.info("自定义注解验证器 UserPermissionEvaluator.");
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
