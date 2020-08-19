package com.hiooih.abdp.system.service;

import com.hiooih.abdp.system.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author duwenlei
 * @since 2020-08-12
 */
public interface ISysUserRoleService extends IService<SysUserRole> {
    List<SysUserRole> selectByUserId(int userId);

}
