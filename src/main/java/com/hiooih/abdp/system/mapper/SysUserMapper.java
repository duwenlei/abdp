package com.hiooih.abdp.system.mapper;

import com.hiooih.abdp.system.entity.SysUser;
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
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select * from sys_user where username = #{username}")
    List<SysUser> selectByUsername(@Param("username") String username);

}
