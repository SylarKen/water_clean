package com.sylar.water_clean.dao.System;

import com.sylar.water_clean.models.Role;
import com.sylar.water_clean.models.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    User getUser(String username);

    Role getRole(int id);

    List<String> getRoleActions(String role_code);

    List<Map<String, Object>> GetUsers();

    Integer Check_Username(Map<String, Object> params);

    Integer Check_Realname(Map<String, Object> params);

    Integer Insert(@Param("params") Map<String, Object> params);

    Integer Update(@Param("params") Map<String, Object> params);

    Integer Delete(Map<String, Object> params);

    Integer Reset(Map<String, Object> params);

    Integer ChangePwd(Map<String, Object> params);
}
