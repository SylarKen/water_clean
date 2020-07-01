package com.sylar.water_clean.controller.Project.Settings.Station;

import com.sylar.water_clean.models.Result;
import com.sylar.water_clean.models.User;
import com.sylar.water_clean.service.Project.Settings.StationService;
import com.sylar.water_clean.utils.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：Sylar
 * @date ：Created in 2019/12/23 9:43
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping(value = "settings/station")
public class Station {
    @Autowired
    StationService service;

    @RequestMapping(value = "/index")
    public String Index() {
        return "project/settings/station/index";
    }

    @ResponseBody
    @RequestMapping(value = "getStation")
    public Object GetStation(HttpServletRequest request, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            Map<String, Object> params = new HashMap<>();
//            params.put("role_code", user.getRole_code());
//            params.put("user_id", user.getId());
            List<Map<String, Object>> data = service.GetStation(params);
            return Result.table(data, data.size());
        } catch (Exception ex) {
            return Result.error("系统异常！");
        }

    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object Save(HttpServletRequest request) {
        try {
            Map<String, Object> params = ParamUtils.getParameterMap(request);
            if (params.get("province_code") == null || params.get("province_code").toString().equals("")) {
                return Result.error("请选择省份!");
            }
            if (params.get("city_code") == null || params.get("city_code").toString().equals("")) {
                return Result.error("请选择城市!");
            }
            if (params.get("latitude") == null || params.get("latitude").toString().equals("") || params.get("longitude") == null || params.get("longitude").toString().equals("")) {
                return Result.error("请在地图上点选详细坐标!");
            }
            if (params.get("IMEI") == null || params.get("IMEI").toString().equals("")) {
                return Result.error("请填写网关编号!");
            } else {
                params.put("IMEI", params.get("IMEI").toString().toUpperCase());
            }
            if (params.get("code") == null || params.get("code").toString().equals("")) {
                return Result.error("请填写站点编号!");
            } else {
                params.put("code", params.get("code").toString().toUpperCase());
            }
            if (params.get("name") == null || params.get("name").toString().equals("")) {
                return Result.error("请填写站点名称!");
            }
            if (params.get("quantity") == null || params.get("quantity").toString().equals("") || params.get("quantity").toString().equals("0")) {
                return Result.error("请填写设备数量!");
            }
            if (params.get("id") == null) {
                // 检查 CODE 是否重复
                if (service.Check_CODE(params)) {
                    // 新添加设备
                    if (service.Check_IMEI(params)) {
                        // 没有重复数据，可直接插入
                        if (service.Insert(params) == 1) {
                            return Result.success("数据保存成功！");
                        } else {
                            return Result.error("数据保存失败，请重试！");
                        }
                    } else {
                        // 有重复数据，提示确认
                        return Result.confirm("该网关已与其他设备绑定，是否先将其解绑！");
                    }
                } else {
                    return Result.error("站点编号已被占用，请修改后重试！");
                }
            } else {
                //修改设备信息
                if (service.Check_ByID(params)) {
                    // 没有重复数据，可直接插入

                    if (service.Update(params) == 1) {
                        return Result.success("数据保存成功！");
                    } else {
                        return Result.error("没有数据被修改！");
                    }
                } else {
                    // 有重复数据，提示确认
                    return Result.confirm("该网关已与其他设备绑定，是否先将其解绑！");
                }
            }
        } catch (Exception ex) {
            return Result.error("系统异常");
        }
    }

    @ResponseBody
    @RequestMapping(value = "unbind")
    public Object Unbind(HttpServletRequest request) {
        try {
            Map<String, Object> params = ParamUtils.getParameterMap(request);

            if (params.get("IMEI") == null) {
                return Result.error("请检查网关编号！");
            } else {
                String IMEI = params.get("IMEI").toString();
                int result = service.Unbind(params);
                return Result.success("网关" + IMEI + "解绑成功！");
            }
        } catch (Exception ex) {
            return Result.error("系统异常！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object Delete(HttpServletRequest request) {
        try {
            Map<String, Object> params = ParamUtils.getParameterMap(request);
            if (params.get("id") == null) {
                return Result.error("未能获取要删除的数据，请刷新重试");
            } else {
                service.Delete(params);
                return Result.success("数据删除成功！");
            }
        } catch (Exception ex) {
            return Result.error("系统异常");
        }
    }
}
