package com.hiooih.abdp.system.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private int id;

    @TableField
    private String fullname;

    @TableField
    private String username;

    @TableField
    private String passwordHash;

    @TableField
    private String salt;

    @TableField
    private String status;

    @TableField
    private Date dataCreated;

    @TableField
    private Date lastUpdated;
}
