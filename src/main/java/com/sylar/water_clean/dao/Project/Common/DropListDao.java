package com.sylar.water_clean.dao.Project.Common;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DropListDao {
    List<Map<String, Object>> GetProvinces();

    List<Map<String, Object>> GetCities();

    List<Map<String, Object>> GetManageGroup();

    List<Map<String, Object>> GetAgents();

    List<Map<String, Object>> GetRoles();

    List<Map<String, Object>> GetStations();
}
