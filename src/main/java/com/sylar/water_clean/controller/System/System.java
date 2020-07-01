package com.sylar.water_clean.controller.System;

import com.sylar.water_clean.models.Menu;
import com.sylar.water_clean.models.Result;
import com.sylar.water_clean.models.User;
import com.sylar.water_clean.service.System.MenuService;
import com.sylar.water_clean.service.System.SystemService;
import com.sylar.water_clean.service.System.UserService;
import com.sylar.water_clean.utils.DigestUtil;
import com.sylar.water_clean.utils.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author ：Sylar
 * @date ：Created in 2019/10/16 9:00
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping(value = "system")
public class System {

    @Autowired
    private UserService service;

    @Autowired
    private MenuService service_menu;
//    @RequestMapping("/")
//    public String index() {
//        return "redirect:/index";
//    }

    @RequestMapping("/index")
    public String index(HttpServletRequest request, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Menu menus = service_menu.GetMenu(user.getRole_code());
        model.addAttribute("menus", menus);
        model.addAttribute("user", user);
        return "index";
    }

    @RequestMapping("/exit")
    public String exit(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }

    @RequestMapping(value = "/noAuth", method = RequestMethod.GET)
    public String noAuth() {
        return "noAuth";
    }


    @ResponseBody
    @RequestMapping(value = "/getUserInfo")
    public Object getUserInfo(String username) {
        return Result.success(service.getUser(username));
    }

    @ResponseBody
    @RequestMapping("/changePwd")
    public Object ChangePwd(HttpServletRequest request, Model model, HttpSession session) {
        try {
            Map<String, Object> params = ParamUtils.getParameterMap(request);
            User user = (User) session.getAttribute("user");
            String pwd_old = "";
            String pwd_new = "";
            String pwd_new_re = "";
            if (params.get("pwd_old") == null || params.get("pwd_old").toString().equals("")) {
                return Result.error("请填写原密码!");
            } else {
                pwd_old = params.get("pwd_old").toString().replace(" ", "");
            }
            if (params.get("pwd_new") == null || params.get("pwd_new").toString().equals("")) {
                return Result.error("请填写新密码!");
            } else {
                pwd_new = params.get("pwd_new").toString().replace(" ", "");
//                params.put("pwd_new", params.get("pwd_new").toString().replace(" ",""));
            }
            if (params.get("pwd_new_re") == null || params.get("pwd_new_re").toString().equals("")) {
                return Result.error("请重复新密码!");
            } else {
                pwd_new_re = params.get("pwd_new_re").toString().replace(" ", "");
//                params.put("pwd_new_re", params.get("pwd_new_re").toString().replace(" ",""));
            }
            String md5pwd_old = DigestUtil.md5(pwd_old);
            if (md5pwd_old.equals(user.getPassword())) {
                if (pwd_new.equals(pwd_new_re)) {
                    String md5pwd = DigestUtil.md5(pwd_new);
                    params.put("username", user.getUsername());
                    params.put("password", md5pwd);
                    service.ChangePwd(params);
                    return Result.success("保存成功！");
                } else {
                    return Result.error("两次输入的新密码不一致，请重新输入!");
                }

            } else {
                return Result.error("原密码输入有误!");
            }


        } catch (Exception ex) {
            return Result.error("系统异常");
        }
    }

}
