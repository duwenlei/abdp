package com.hiooih.abdp.system.entity;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hiooih.base.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author duwenlei
 * @since 2020-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_user_role")
public class SysUserRole extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField
    private Integer userId;

    @TableField
    private Integer roleId;
}
