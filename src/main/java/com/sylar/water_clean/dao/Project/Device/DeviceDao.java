package com.sylar.water_clean.dao.Project.Device;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeviceDao {
    List<Map<String,Object>> GetDevices_ByID(int id);
}
