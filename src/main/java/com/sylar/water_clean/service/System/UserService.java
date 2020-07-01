package com.sylar.water_clean.service.System;

import com.sylar.water_clean.dao.System.UserDao;
import com.sylar.water_clean.models.Role;
import com.sylar.water_clean.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ：Sylar
 * @date ：Created in 2020/1/13 9:51
 * @description：
 * @modified By：
 * @version: $
 */

@Service
public class UserService {
    @Autowired
    UserDao dao;

    public User getUser(String username) {
        User user = dao.getUser(username);
        if (user != null)
            user.setActions(dao.getRoleActions(user.getRole_code()));
        return user;
    }

    public Role getRole(int id) {
        Role role = dao.getRole(id);

        return role;
    }

//    @Cacheable(cacheNames = "system.actions")
//    public List<String> getAllActions(){
//        return dao.getAllActions();
//    }

    public List<Map<String, Object>> GetUsers() {
        return dao.GetUsers();
    }

    public boolean Check_Username(Map<String, Object> params) {
        return dao.Check_Username(params) == 0;
    }

    public boolean Check_Realname(Map<String, Object> params) {
        return dao.Check_Realname(params) == 0;
    }

    public Integer Insert(Map<String, Object> params) {
        return dao.Insert(params);
    }

    public Integer Update(Map<String, Object> params) {
        return dao.Update(params);
    }

    public Integer Delete(Map<String, Object> params) {
        return dao.Delete(params);
    }

    public Integer Reset(Map<String, Object> params) {
        return dao.Reset(params);
    }

    public boolean ChangePwd(Map<String, Object> params) {
        return dao.ChangePwd(params) == 1;
    }
}
