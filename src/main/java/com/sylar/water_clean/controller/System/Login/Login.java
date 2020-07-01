package com.sylar.water_clean.controller.System.Login;

import com.sylar.water_clean.models.Result;
import com.sylar.water_clean.models.User;
import com.sylar.water_clean.service.System.UserService;
import com.sylar.water_clean.utils.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ：Sylar
 * @date ：Created in 2020/1/13 9:30
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
public class Login {
    @Autowired
    private UserService service;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login_view() {
        return "/system/login";
    }

    @ResponseBody
    @RequestMapping(value = "/login_form")
    public Object login(HttpServletRequest request, String username, String password) {
        try {
            String md5pwd = DigestUtil.md5(password);
            User user = service.getUser(username);
            if (user == null) {
                return Result.error("手机号或用户名不存在！");
            } else if (!user.getPassword().equals(md5pwd)) {
                return Result.error("密码输入错误!");
            } else {
                request.getSession().setAttribute("user", user);
            }
            return Result.success("登录成功！");
        } catch (Exception ex) {
            return Result.error("系统异常！");
        }
    }

    @RequestMapping("/exit")
    public String exit(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }

    @RequestMapping(value = "/timeOut", method = RequestMethod.GET)
    public String timeOut() {
        return "timeOut";
    }
}
