package com.hiooih.abdp.system.entity;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class SysUserRole implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private int id;

    @TableField
    private Integer userId;

    @TableField
    private Integer roleId;

    @TableField
    private Date dataCreated;

    @TableField
    private Date lastUpdated;


}