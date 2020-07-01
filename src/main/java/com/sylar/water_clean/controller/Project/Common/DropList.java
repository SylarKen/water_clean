package com.sylar.water_clean.controller.Project.Common;

import com.sylar.water_clean.models.Result;
import com.sylar.water_clean.service.Project.Common.DropListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：Sylar
 * @date ：Created in 2019/12/26 9:40
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping(value = "common/droplist")
public class DropList {
    @Autowired
    DropListService service;

    @ResponseBody
    @RequestMapping(value = "/getareas")
    public Object GetAreas() {
        return Result.success(service.GetAreas());
    }

    @ResponseBody
    @RequestMapping(value = "/getagents")
    public Object GetAgents() {
        return Result.success(service.GetAgents());
    }

    @ResponseBody
    @RequestMapping(value = "/getuserform")
    public Object GetUserForm_DropList() {
        return Result.success(service.GetUserForm_DropList());
    }
}
