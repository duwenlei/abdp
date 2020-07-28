package com.hiooih.abdp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author duwenlei
 **/
@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/main")
    public ModelAndView main() {
        return new ModelAndView("views/main");
    }
}
