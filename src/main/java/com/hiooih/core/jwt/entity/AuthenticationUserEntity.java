package com.hiooih.core.jwt.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 存放认证信息主体类
 *
 * @author duwenlei
 **/
@Getter
@Setter
public class AuthenticationUserEntity {
    private String userName;
    private String passwordHash;

    private List<String> authorities;


    public Map<String, Object> getMap() {
        Map<String, Object> entityMap = new HashMap<>(2);
        entityMap.put("userName", userName);
        entityMap.put("authorities", authorities);
        return entityMap;
    }
}
