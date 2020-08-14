package com.hiooih.abdp.system.controller;


import com.hiooih.abdp.system.entity.SysUser;
import com.hiooih.abdp.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author duwenlei
 * @since 2020-08-12
 */
@RestController
@RequestMapping("/system/sysUser")
public class SysUserController {

    @Autowired
    @Qualifier("sysUserService")
    private ISysUserService userService;

    @PostMapping("/save")
    public String save() {
        SysUser sysUser = new SysUser();
        sysUser.setDataCreated(new Date());
        sysUser.setLastUpdated(new Date());
        sysUser.setUsername("dwl");
        sysUser.setStatus("正常");
        sysUser.setFullname("杜文磊");
        sysUser.setPasswordHash("aaaaa");
        sysUser.setSalt("bbbbb");

        userService.save(sysUser);
        return "success";
    }
}

