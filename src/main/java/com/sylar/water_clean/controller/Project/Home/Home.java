package com.sylar.water_clean.controller.Project.Home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：Sylar
 * @date ：Created in 2019/12/21 15:25
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping(value = "/home")
public class Home {
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request){
        return "project/home/index";
    }


}
