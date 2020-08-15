package com.hiooih.base.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.Objects;

/**
 * 自定义用户访问决策者
 *
 * @author duwenlei
 **/
@Slf4j
public class UserAccessDecisionVoter implements AccessDecisionVoter<Object> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     * @param authentication 调用者身份
     * @param object         被调用的对象
     * @param attributes     与受保护对象关联的配置属性
     * @return
     */
    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if (Objects.isNull(authentication)) {
            // 拒绝访问
            return ACCESS_DENIED;
        }
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String requestUrl = filterInvocation.getRequestUrl();
        log.info("自定义用户访问决策，拦截的路径：[{}]", requestUrl);

        //TODO 获取数据中定义当前请求路径所需要的权限

        // 当前用户的角色列表
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        // 当前访问地址需要的角色
        // 应该是去数据库中或者缓存中去查询，目前先简单判断一下，取地址第一个/*/中的内容
        String role = requestUrl.substring(requestUrl.indexOf("/") + 1, requestUrl.indexOf("/", 2));
        log.info("当前路径需要的角色为：[{}]", role);


        // 取得调用者的所有权限

        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            if (grantedAuthority.getAuthority().equalsIgnoreCase(role)) {
                // 授权访问
                return ACCESS_GRANTED;
            }
        }

        return ACCESS_DENIED;
    }

    @Override
    public boolean supports(Class clazz) {
        return true;
    }
}
