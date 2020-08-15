package com.hiooih.base.mybatis.entity;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author duwenlei
 **/
@Component
public class InitAuditMetaObjectHandlerBean {

    @Bean
    @ConditionalOnMissingBean(name = "auditMetaObjectHandler")
    public AuditMetaObjectHandler auditMetaObjectHandler() {
        return new AuditMetaObjectHandler();
    }
}
