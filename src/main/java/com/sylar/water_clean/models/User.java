package com.sylar.water_clean.models;

import lombok.Data;

import java.util.List;

/**
 * @author ：Sylar
 * @date ：Created in 2019/10/16 9:13
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class User {
    private Integer id;
    private String username;// 用户名称
    private String password;// 用户密码
    private String realname;// 用户姓名
    private String phone;// 手机
    private Integer role;//角色ID
    private String role_code;// 角色code
    private String roleName;// 角色名称
    private List<String> Actions;// 用户拥有的菜单权限url

}
