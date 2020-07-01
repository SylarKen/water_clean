package com.sylar.water_clean.dao.System;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MenuDao {
    List<Map<String,Object>> Get();
    List<Map<String,Object>> GetMenuByRole(String role_code);
    List<Map<String, Object>> GetEquipments();
    List<String> getAllActions();
}
