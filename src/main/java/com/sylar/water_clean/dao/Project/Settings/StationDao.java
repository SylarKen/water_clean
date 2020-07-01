package com.sylar.water_clean.dao.Project.Settings;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ：Sylar
 * @date ：Created in 2019/12/23 14:47
 * @description：
 * @modified By：
 * @version: $
 */
@Mapper
public interface StationDao {
    List<Map<String, Object>> GetStation(Map<String, Object> params);
    Integer Check_CODE(Map<String, Object> params);
    Integer Check_IMEI(Map<String, Object> params);

    Integer Check_ByID(Map<String, Object> params);

    Integer Insert(@Param("params") Map<String, Object> params);

    Integer Update(@Param("params") Map<String, Object> params);

    Integer Unbind(Map<String, Object> params);

    Integer Delete(Map<String, Object> params);

    List<Map<String, Object>> GetStation_ByIMEI(String IMEI);


}
