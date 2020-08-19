package com.hiooih.abdp.system.mapper;

import com.hiooih.abdp.system.entity.SysRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author duwenlei
 * @since 2020-08-12
 */
@Repository
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    @Select("select * from sys_role_permission where role_id= #{roleId}")
    List<SysRolePermission> selectByRoleId(int roleId);

}
