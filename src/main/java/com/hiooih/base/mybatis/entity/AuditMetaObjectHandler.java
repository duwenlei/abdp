package com.hiooih.base.mybatis.entity;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 实现审计功能
 *
 * @author duwenlei
 **/
public class AuditMetaObjectHandler implements MetaObjectHandler {
    private static final String DATA_CREATED_ENTITY_FIELD = "dataCreated";
    private static final String LAST_UPDATED_ENTITY_FIELD = "lastUpdated";

    @Override
    public void insertFill(MetaObject metaObject) {
        boolean dataCreated = metaObject.hasSetter(DATA_CREATED_ENTITY_FIELD);
        boolean lastUpdated = metaObject.hasSetter(LAST_UPDATED_ENTITY_FIELD);

        Date now = new Date();
        if (dataCreated) {
            this.setFieldValByName(DATA_CREATED_ENTITY_FIELD, now, metaObject);
        }
        if (lastUpdated) {
            this.setFieldValByName(LAST_UPDATED_ENTITY_FIELD, now, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        boolean lastUpdated = metaObject.hasSetter(LAST_UPDATED_ENTITY_FIELD);
        if (lastUpdated) {
            this.setFieldValByName(LAST_UPDATED_ENTITY_FIELD, new Date(), metaObject);
        }
    }
}
