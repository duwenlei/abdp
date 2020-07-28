package com.hiooih.abdp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author duwenlei
 **/
@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("views/user/create");
    }
}
