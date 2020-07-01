package com.sylar.water_clean.controller.System;

import com.sylar.water_clean.models.User;
import com.sylar.water_clean.service.System.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author ：Sylar
 * @date ：Created in 2019/8/1 9:54
 * @description：Draw Menus
 * @modified By：
 * @version: $
 */

@Controller
@RequestMapping(value = "Menus")
public class Menus {
    @Autowired
    private MenuService service_menu;

    @RequestMapping(value = "/get")
    @ResponseBody
    public Object get(HttpSession session) {
        User user = (User) session.getAttribute("user");
//        return service_menu.GetMenu(user.getRole());
        return service_menu.GetMenu();
    }




}
