package com.sylar.water_clean.controller.Project.Settings.Users;

import com.sylar.water_clean.models.Result;
import com.sylar.water_clean.service.System.UserService;
import com.sylar.water_clean.utils.DigestUtil;
import com.sylar.water_clean.utils.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author ：Sylar
 * @date ：Created in 2020/1/14 15:11
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping(value = "settings/users/")
public class Users {
    @Autowired
    UserService service;

    @RequestMapping(value = "index")
    public String Index(HttpServletRequest request) {
        return "project/settings/users/index";
    }

    @ResponseBody
    @RequestMapping(value = "getUsers")
    public Object GetUsers(HttpServletRequest request) {
        List<Map<String, Object>> data = service.GetUsers();
        return Result.table(data, data.size());
    }

    @ResponseBody
    @RequestMapping(value = "save")
    public Object Save(HttpServletRequest request) {
        try {
            Map<String, Object> params = ParamUtils.getParameterMap(request);
            if (params.get("role") == null || params.get("role").toString().equals("")) {
                return Result.error("请选择角色!");
            }
            if (params.get("role").toString().replace(" ", "").equals("agent") && (params.get("agent") == null || params.get("agent").toString().equals(""))) {
                return Result.error("请选择代理商!");
            }
            if (params.get("role").toString().replace(" ", "").equals("stationmaster") && (params.get("station") == null || params.get("station").toString().equals(""))) {
                return Result.error("请选择站点!");
            }
            if (params.get("username") == null || params.get("username").toString().equals("")) {
                return Result.error("请填写登录名称!");
            }
            if (params.get("realname") == null || params.get("realname").toString().equals("")) {
                return Result.error("请填写用户名称!");
            }
            params.put("password", DigestUtil.md5("123456"));
//            if (params.get("id") == null) {
            //新添加用户

            if (params.get("id") == null) {
                // 检查登录名和昵称
                if (!service.Check_Username(params)) {
                    // 登录名称重复
                    return Result.error("登录名已被占用，请更换后重试！");

                }
                if (!service.Check_Realname(params)) {
                    // 登录名称重复
                    return Result.error("用户名已被占用，请更换后重试！");

                }
                if (service.Insert(params) == 1) {
                    return Result.success("数据保存成功！");
                } else {
                    return Result.error("数据保存失败，请重试！");
                }
            } else {
                // 修改时不允许修改登录名和昵称,所以不需要检查
                if (service.Update(params) == 1) {
                    return Result.success("数据保存成功！");
                } else {
                    return Result.error("没有数据被修改！");
                }
            }
        } catch (Exception ex) {
            return Result.error("系统异常");
        }

    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object Delete(HttpServletRequest request) {
        try {
            Map<String, Object> params = ParamUtils.getParameterMap(request);
            if (params.get("id") == null) {
                return Result.error("未能获取要删除的用户信息，请刷新重试");
            } else {
                service.Delete(params);
                return Result.success("数据删除成功！");
            }
        } catch (Exception ex) {
            return Result.error("系统异常");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/reset")
    public Object Reset(HttpServletRequest request) {
        try {
            Map<String, Object> params = ParamUtils.getParameterMap(request);
            if (params.get("id") == null) {
                return Result.error("未能获取要重置密码的用户信息，请刷新重试");
            } else {
                service.Reset(params);
                return Result.success("密码重置成功！");
            }
        } catch (Exception ex) {
            return Result.error("系统异常");
        }
    }
}
