package com.sylar.water_clean.service.Project.Common;

import com.sylar.water_clean.dao.Project.Common.DropListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：Sylar
 * @date ：Created in 2019/12/26 10:16
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class DropListService {
    @Autowired
    DropListDao dao;

    public Map<String, Object> GetAreas() {
        List<Map<String, Object>> provinces = dao.GetProvinces();
        List<Map<String, Object>> cities = dao.GetCities();
        Map<String, Object> data = new HashMap<>();
        data.put("provinces", provinces);
        data.put("cities", cities);
        return data;
    }

    public Map<String, Object> GetAgents() {
        List<Map<String, Object>> group = dao.GetAgents();
        Map<String, Object> data = new HashMap<>();
        data.put("group", group);
        return data;
    }

    public Map<String, Object> GetUserForm_DropList() {
        List<Map<String, Object>> roles = dao.GetRoles();
        List<Map<String, Object>> agents = dao.GetAgents();
        List<Map<String, Object>> stations = dao.GetStations();
        Map<String, Object> data = new HashMap<>();
        data.put("roles", roles);
        data.put("agents", agents);
        data.put("stations", stations);
        return data;
    }
}
