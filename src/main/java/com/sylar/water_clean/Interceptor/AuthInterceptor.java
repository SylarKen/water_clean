package com.sylar.water_clean.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.sylar.water_clean.models.Result;
import com.sylar.water_clean.models.User;
import com.sylar.water_clean.service.System.MenuService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author ：Sylar
 * @date ：Created in 2019/10/16 10:58
 * @description：身份验证拦截器
 * @modified By：
 * @version: $
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Resource
    private MenuService menuService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println(request.getRequestURI());
        // 验证session是否存在
        Object obj = request.getSession().getAttribute("user");
        String requestType = request.getHeader("X-Requested-With");
        String contentType = request.getHeader("Content-Type");
        String method = request.getMethod();
        if (obj == null) {
            if ("XMLHttpRequest".equals(requestType)) {
                if ("view".equals(contentType)) {
                    response.sendRedirect("/timeOut");
                } else {
                    sendJsonMessage(response, Result.timeout());
                }
            } else {
                response.sendRedirect("/timeOut");
            }
            return false;
        } else {
            // 从数据库中获取所有权限URL，并判断当前路径是否包含在数据库
            String requestURI = request.getRequestURI();
            List<String> menus = menuService.getAllActions();
            //如果存在，需要进行验证是否授权，如果不存在，放行
            if (menus.contains(requestURI)) {
                List<String> userURls = ((User) obj).getActions();
                if (!userURls.contains(requestURI)) {
                    if (method.equals("POST")) {
                        sendJsonMessage(response, Result.noAuth());
                    } else {
                        response.sendRedirect("/noAuth");
                    }
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

    private static void sendJsonMessage(HttpServletResponse response, Object obj) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(obj));
        writer.close();
        response.flushBuffer();
    }
}
