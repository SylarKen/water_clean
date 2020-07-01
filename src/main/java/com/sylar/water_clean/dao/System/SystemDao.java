package com.sylar.water_clean.dao.System;

import com.sylar.water_clean.models.Role;
import com.sylar.water_clean.models.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemDao {

    User getUser(String username);
    Role getRole(int id);
    List<String> getAllActions();

    List<String> getRoleActions(int role);

//    List<Menu> getRoleMenus(int role);

}
