package com.hiooih.abdp.system.mapper;

import com.hiooih.abdp.system.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    @Select("select * from sys_user_role where user_id=#{userId}")
    List<SysUserRole> selectByUserId(int userId);

}
