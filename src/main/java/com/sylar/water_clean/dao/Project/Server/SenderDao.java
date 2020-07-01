package com.sylar.water_clean.dao.Project.Server;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SenderDao {
    List<Map<String,Object>> CheckSender(String ICCID);

//    List<Sender> GetSenders();
    List<Map<String,Object>> GetData_By_ICCID(Map<String, Object> params);
    List<Map<String,Object>> GetData();

    int Insert(Map<String, Object> params);
}
