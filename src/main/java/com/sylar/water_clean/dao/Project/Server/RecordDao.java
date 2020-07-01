package com.sylar.water_clean.dao.Project.Server;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecordDao {
    int Insert_VI(Map<String, Object> params);

    int Insert_IO(Map<String, Object> params);

    int Update_Realtime_VI(Map<String, Object> params);

    int Update_Realtime_IO(Map<String, Object> params);

    List<Map<String, Object>> CheckBind(Map<String, Object> params);
}
