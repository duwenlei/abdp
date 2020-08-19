package com.hiooih.abdp.system.service.impl;

import com.hiooih.abdp.system.entity.SysUserRole;
import com.hiooih.abdp.system.mapper.SysUserRoleMapper;
import com.hiooih.abdp.system.service.ISysUserRoleService;
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
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysUserRole> selectByUserId(int userId) {
        return sysUserRoleMapper.selectByUserId(userId);
    }
}
