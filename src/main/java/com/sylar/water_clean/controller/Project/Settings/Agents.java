package com.sylar.water_clean.controller.Project.Settings;

import com.sylar.water_clean.models.Result;
import com.sylar.water_clean.service.Project.Settings.AgentService;
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
 * @date ：Created in 2020/1/17 10:47
 * @description：代理商管理
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping(value = "settings/agents/")
public class Agents {
    @Autowired
    AgentService service;

    @RequestMapping(value = "index")
    public String Index(HttpServletRequest request) {
        return "project/settings/agents/index";
    }

    @ResponseBody
    @RequestMapping(value = "getAgents")
    public Object GetAgents(HttpServletRequest request) {
        List<Map<String, Object>> data = service.GetAgents();
        return Result.table(data, data.size());
    }

    @ResponseBody
    @RequestMapping(value = "get_stations")
    public Object GetStations(HttpServletRequest request) {
        Map<String, Object> params = ParamUtils.getParameterMap(request);
        List<Map<String, Object>> data = service.GetStations(params);
        return Result.success(data);
    }
    @ResponseBody
    @RequestMapping(value = "save_agent")
    public Object SaveAgents(HttpServletRequest request) {
        try {
            Map<String, Object> params = ParamUtils.getParameterMap(request);
            if (params.get("code") == null || params.get("code").toString().equals("")) {
                return Result.error("请填写编号!");
            }else{
                params.put("code", params.get("code").toString().replace(" ",""));
            }
            if (params.get("name") == null || params.get("name").toString().equals("")) {
                return Result.error("请填写名称!");
            }else{
                params.put("name", params.get("name").toString().replace(" ",""));
            }
            if(!service.Check_Code(params)){
                return Result.error("该编号已被占用!");
            }
            if(!service.Check_Name(params)){
                return Result.error("该名称已被占用!");
            }
            service.SaveAgents(params);
            return Result.success("保存成功！");
        } catch (Exception ex) {
            return Result.error("系统异常");
        }

    }
    @ResponseBody
    @RequestMapping(value = "save_stations")
    public Object SaveStations(HttpServletRequest request) {
        try {
            Map<String, Object> params = ParamUtils.getParameterMap(request);
            service.UpdateStations(params);
            return Result.success("保存成功！");
        } catch (Exception ex) {
            return Result.error("系统异常");
        }

    }
}
