package com.sylar.water_clean.controller;

import com.sylar.water_clean.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ：Sylar
 * @date ：Created in 2019/11/15 9:27
 * @description：Frame
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping(value = "/")
public class Index {
    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request, HttpSession session) {
        ModelAndView view = new ModelAndView();
        try {
            User user = (User) session.getAttribute("user");
            view.addObject("realname", user.getRealname());
            view.setViewName("index");
        } catch (Exception ex) {
            view.setViewName("redirect:/login");

        }
        return view;
    }
}
