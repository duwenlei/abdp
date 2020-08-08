package com.hiooih.abdp.system.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duwenlei
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public String login() {
        return "登录成功";
    }

    @PostMapping("/logout")
    public String logout() {
        return "退出成功";
    }
}
