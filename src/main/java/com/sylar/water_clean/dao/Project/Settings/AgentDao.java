package com.sylar.water_clean.dao.Project.Settings;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AgentDao {
    List<Map<String, Object>> GetAgents();

    List<Map<String, Object>> GetStations(Map<String, Object> params);

    Integer UpdateStations_Reset(Map<String, Object> params);

    Integer UpdateStations_Set(Map<String, Object> params);

    Integer Check_Code(Map<String, Object> params);

    Integer Check_Name(Map<String, Object> params);

    Integer SaveAgents(Map<String, Object> params);
}
