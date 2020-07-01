package com.sylar.water_clean.service.Project.Server;


import com.sylar.water_clean.dao.Project.Server.RecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ：Sylar
 * @date ：Created in 2019/11/12 14:12
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class RecordService {
    @Autowired
    private RecordDao dao;

    public int Insert_VI(Map<String, Object> params) {
        return dao.Insert_VI(params);
    }

    public int Insert_IO(Map<String, Object> params) {
        return dao.Insert_IO(params);
    }

    // 更新实时数据 VI
    public int Update_Realtime_VI(Map<String, Object> params) {
        return dao.Update_Realtime_VI(params);
    }

    // 更新实时数据 IO
    public int Update_Realtime_IO(Map<String, Object> params) {
        return dao.Update_Realtime_IO(params);
    }

    public boolean CheckBind(Map<String, Object> params) {
        List<Map<String, Object>> data = dao.CheckBind(params);
        return data.size() == 1;
    }
}
