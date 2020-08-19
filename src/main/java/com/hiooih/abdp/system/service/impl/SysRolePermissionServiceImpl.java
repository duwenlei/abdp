package com.hiooih.abdp.system.service.impl;

import com.hiooih.abdp.system.entity.SysRolePermission;
import com.hiooih.abdp.system.mapper.SysRolePermissionMapper;
import com.hiooih.abdp.system.service.ISysRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author duwenlei
 * @since 2020-08-12
 */
@Service("sysRolePermissionService")
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysRolePermission> selectByRoleId(int roleId) {
        return sysRolePermissionMapper.selectByRoleId(roleId);
    }
}
