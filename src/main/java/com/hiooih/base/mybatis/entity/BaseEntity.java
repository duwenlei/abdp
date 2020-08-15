package com.hiooih.base.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Entity 实体基类
 *
 * @author duwenlei
 **/
@Getter
@Setter
public class BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(fill = FieldFill.INSERT)
    private Date dataCreated;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdated;
}
