package com.sylar.water_clean.service.Project.Settings;

import com.sylar.water_clean.dao.Project.Settings.StationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ：Sylar
 * @date ：Created in 2019/12/23 14:46
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class StationService {
    @Autowired
    StationDao dao;

    public List<Map<String, Object>> GetStation(Map<String, Object> params) {
        return dao.GetStation(params);
    }
    public boolean Check_CODE(Map<String, Object> params) {
        return dao.Check_CODE(params) == 0;
    }
    public boolean Check_IMEI(Map<String, Object> params) {
        return dao.Check_IMEI(params) == 0;
    }
    public boolean Check_ByID(Map<String, Object> params) {
        return dao.Check_ByID(params) == 0;
    }
    public Integer Insert(Map<String, Object> params) {
        return dao.Insert(params);
    }

    public Integer Update(Map<String, Object> params) {
        return dao.Update(params);
    }

    public Integer Unbind(Map<String, Object> params) {
        return dao.Unbind(params);
    }
    public Integer Delete(Map<String, Object> params) {
        return dao.Delete(params);
    }
    public List<Map<String, Object>> GetStation_ByIMEI(String IMEI) {
        return dao.GetStation_ByIMEI(IMEI);
    }


}
