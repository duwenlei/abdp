package com.hiooih.base.auth;

import com.hiooih.abdp.system.entity.SysPermission;
import com.hiooih.abdp.system.entity.SysRolePermission;
import com.hiooih.abdp.system.entity.SysUser;
import com.hiooih.abdp.system.entity.SysUserRole;
import com.hiooih.abdp.system.service.ISysPermissionService;
import com.hiooih.abdp.system.service.ISysRolePermissionService;
import com.hiooih.abdp.system.service.ISysUserRoleService;
import com.hiooih.abdp.system.service.ISysUserService;
import com.hiooih.core.jwt.entity.AuthenticationUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 登录验证
 *
 * @author duwenlei
 **/
@Component
@Slf4j
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("sysUserService")
    private ISysUserService sysUserService;

    @Autowired
    @Qualifier("sysUserRoleService")
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    @Qualifier("sysPermissionService")
    private ISysPermissionService sysPermissionService;

    @Autowired
    @Qualifier("sysRolePermissionService")
    private ISysRolePermissionService sysRolePermissionService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        // 查寻用户名是否存在
        List<SysUser> sysUsers = sysUserService.selectByUsername(username);
        if (sysUsers.size() != 1) {
            throw new UsernameNotFoundException("用户名填写有误。");
        }

        // 比对密码是否一致
        SysUser sysUser = sysUsers.get(0);
        if (!sysUser.getPasswordHash().equals(password)) {
            throw new CredentialsExpiredException("密码有误。");
        }

        AuthenticationUserEntity entity = new AuthenticationUserEntity();
        entity.setUsername(username);
        entity.setUserId(sysUser.getId());

        // 存入 ThreadLocal 中
        return  new UsernamePasswordAuthenticationToken(entity, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
