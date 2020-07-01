package com.sylar.water_clean.service.Project.Device;

import com.sylar.water_clean.dao.Project.Device.DeviceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ：Sylar
 * @date ：Created in 2020/1/9 16:13
 * @description：设备相关
 * @modified By：
 * @version: $
 */
@Service
public class DeviceService {
    @Autowired
    DeviceDao dao;
    public List<Map<String,Object>> GetDevices_ByID(int id){
        return dao.GetDevices_ByID(id);
    }
}
