package com.hiooih.abdp.system.service;

import com.hiooih.abdp.system.entity.SysRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author duwenlei
 * @since 2020-08-12
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {
    List<SysRolePermission> selectByRoleId(int roleId);
}
