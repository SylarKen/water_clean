package com.sylar.water_clean.service.Project.Settings;

import com.sylar.water_clean.dao.Project.Settings.AgentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ：Sylar
 * @date ：Created in 2020/1/17 14:07
 * @description：代理商管理
 * @modified By：
 * @version: $
 */
@Service
public class AgentService {
    @Autowired
    AgentDao dao;

    public List<Map<String, Object>> GetAgents() {
        return dao.GetAgents();
    }

    public List<Map<String, Object>> GetStations(Map<String, Object> params) {
        return dao.GetStations(params);
    }

    public boolean Check_Code(Map<String, Object> params) {
        Integer count = dao.Check_Code(params);
        return count == 0;
    }

    public boolean Check_Name(Map<String, Object> params) {
        Integer count = dao.Check_Name(params);
        return count == 0;
    }
    public Integer  SaveAgents(Map<String, Object> params) {
        return dao.SaveAgents(params);
    }

    public Integer UpdateStations(Map<String, Object> params) {
        dao.UpdateStations_Reset(params);
        return dao.UpdateStations_Set(params);
    }
}
