package com.sylar.water_clean.controller.Project.Details;

import com.sylar.water_clean.models.Result;
import com.sylar.water_clean.service.Project.Device.DeviceService;
import com.sylar.water_clean.service.Project.Settings.StationService;
import com.sylar.water_clean.utils.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author ：Sylar
 * @date ：Created in 2019/12/30 10:23
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping(value = "details")
public class Details {
    @Autowired
    DeviceService service;

    @RequestMapping(value = "/index")
    public ModelAndView Index(HttpServletRequest request) {
        Map<String, Object> params = ParamUtils.getParameterMap(request);
        ModelAndView view = new ModelAndView();
        if (params.get("id") == null) {
            String requestUrl = request.getHeader("referer");
            view.setViewName("project/home/index");
        } else {

            view.setViewName("project/details");
            view.addObject("id", params.get("id"));
        }

        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/get_devices")
    public Object GetDevices(HttpServletRequest request) {
        try {
            Map<String, Object> params = ParamUtils.getParameterMap(request);

            if (params.get("id") == null) {
                return Result.error("未选中站点！");
            } else {
                String id_str = params.get("id").toString();
                Integer id = Integer.parseInt(id_str);
                List<Map<String, Object>> data = service.GetDevices_ByID(id);
                return Result.success(data);
            }
        } catch (Exception ex) {
            return Result.error("系统异常！");
        }
    }
}
