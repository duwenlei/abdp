package com.hiooih.abdp.system.service.impl;

import com.hiooih.abdp.system.entity.SysUser;
import com.hiooih.abdp.system.mapper.SysUserMapper;
import com.hiooih.abdp.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author duwenlei
 * @since 2020-08-12
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
