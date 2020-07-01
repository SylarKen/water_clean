package com.sylar.water_clean.service.Project.Server;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ：Sylar
 * @date ：Created in 2019/11/13 10:50
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class SenderService {
//    @Autowired
//    SenderDao dao;
//    public boolean CheckSender(String ICCID){
//        List<Map<String,Object>> senderList=dao.CheckSender(ICCID);
//        if(senderList.size()==1){
//            return true;
//        }else{
//            return false;
//        }
//
//    }
//
////    public List<Map<String,Object>> GetSenders(){
////        List<Map<String,Object>> queryList = dao.GetSenders();
////        queryList.forEach((q) -> {
////            q.put("time", q.get("time_last_active") == null ? "" : CommonUtil.DateToString((Date) q.get("time_last_active"), "yyyy-MM-dd HH:mm:ss"));
////
////        });
////        return queryList;
////    }
//
//    public List<Sender> GetSenders(){
//        List<Sender> queryList = dao.GetSenders();
//        return queryList;
//    }
//
//    public List<Map<String,Object>> GetData_By_ICCID(Map<String,Object> params){
//        return dao.GetData_By_ICCID(params);
//    }
//
//    public int Insert(Map<String,Object> params){
//        return dao.Insert(params);
//    }
}
