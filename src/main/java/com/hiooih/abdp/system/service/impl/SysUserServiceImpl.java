package com.hiooih.abdp.system.service.impl;

import com.hiooih.abdp.system.entity.SysPermission;
import com.hiooih.abdp.system.entity.SysRolePermission;
import com.hiooih.abdp.system.entity.SysUser;
import com.hiooih.abdp.system.entity.SysUserRole;
import com.hiooih.abdp.system.mapper.SysUserMapper;
import com.hiooih.abdp.system.service.ISysRolePermissionService;
import com.hiooih.abdp.system.service.ISysUserRoleService;
import com.hiooih.abdp.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hiooih.core.jwt.entity.AuthenticationUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author duwenlei
 * @since 2020-08-12
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    @Qualifier("sysUserRoleService")
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    @Qualifier("sysRolePermissionService")
    private ISysRolePermissionService sysRolePermissionService;


    @Override
    public List<SysUser> selectByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    @Override
    public Authentication getUserAuthentication(SysUser user) {
        // 权限
        Set<GrantedAuthority> grants = new HashSet<>();

        // 先查询当前用户所有角色
        List<SysUserRole> sysUserRoles = sysUserRoleService.selectByUserId(user.getId());
        // 在根据角色，查询所有权限
        for (SysUserRole userRole : sysUserRoles) {
            grants.add(new SimpleGrantedAuthority(userRole.getRoleId().toString()));
        }

        AuthenticationUserEntity entity = new AuthenticationUserEntity();
        entity.setUsername(user.getUsername());

        return new UsernamePasswordAuthenticationToken(entity, null, grants);
    }
}
