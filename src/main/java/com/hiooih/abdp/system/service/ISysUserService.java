package com.hiooih.abdp.system.service;

import com.hiooih.abdp.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author duwenlei
 * @since 2020-08-12
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return
     */
    List<SysUser> selectByUsername(String username);

    /**
     * 获取用户的 authentication
     *
     * @return
     */
    Authentication getUserAuthentication(SysUser user);
}
