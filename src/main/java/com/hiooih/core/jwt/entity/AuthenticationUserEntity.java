package com.hiooih.core.jwt.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放认证信息主体类
 *
 * @author duwenlei
 **/
public class AuthenticationUserEntity {
    private String userName;
    private String passwordHash;

    public Map<String, Object> getMap() {
        Map<String, Object> entityMap = new HashMap<>(2);
        entityMap.put("userName", userName);
        entityMap.put("passwordHash", passwordHash);
        return entityMap;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
