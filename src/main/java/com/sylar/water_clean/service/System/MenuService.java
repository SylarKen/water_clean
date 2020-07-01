package com.sylar.water_clean.service.System;

import com.sylar.water_clean.dao.System.MenuDao;
import com.sylar.water_clean.models.Menu;
import com.sylar.water_clean.models.Menu_Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：Sylar
 * @date ：Created in 2019/8/1 10:00
 * @description：Service For Menus
 * @modified By：
 * @version: $
 */
@Service
public class MenuService {
    @Autowired
    private MenuDao dao;

//    @Autowired
//    private SystemDao dao_sys;

    public List<Map<String, Object>> Get() {
        return dao.Get();
    }

    public List<Map<String, Object>> GetMenuByRole(String role_code) {
        return dao.GetMenuByRole(role_code);
    }

    public List<String> getAllActions() {
        return dao.getAllActions();
    }

    public Menu GetMenu() {
        try {

            List<Map<String, Object>> menus = Get();
            List<Map<String, Object>> top = menus.stream().filter(f -> f.get("pid").equals(0)).collect(Collectors.toList());
            Menu menu = new Menu();
            treeData(menu.nodes, top, menus);

            menu.menus_all = menus;
            return menu;
        } catch (Exception ex) {
            return null;
        }

    }

    public Menu GetMenu(@Nullable String role_code) {
        try {
//            Role role = dao_sys.getRole(role_id);
//            if(role.getKey().toUpperCase().equals("CLIENT")){
//
//            }
            List<Map<String, Object>> menus;
            if (role_code == null || role_code.replace(" ","").equals("")) {
                menus = Get();
            } else {
                menus = GetMenuByRole(role_code);
            }
            List<Map<String, Object>> top = menus.stream().filter(f -> f.get("pid").equals(0)).collect(Collectors.toList());
            Menu menu = new Menu();
            treeData(menu.nodes, top, menus);

            menu.menus_all = menus;
            return menu;
        } catch (Exception ex) {
            return null;
        }

    }

    private void treeData(List<Menu_Node> menuNodes, List<Map<String, Object>> parents, List<Map<String, Object>> all) {
//        List<Object> list = new ArrayList<>();
        parents.forEach(p -> {
            Menu_Node menuNode = new Menu_Node();
            menuNode.nodeName = p.get("title").toString();
            menuNode.nodeCode = p.get("name").toString();
            menuNode.url = p.get("url") == null ? "" : p.get("url").toString();
            menuNode.icon = p.get("icon") == null ? "" : p.get("icon").toString();
            menuNode.children = new ArrayList<>();
            List<Map<String, Object>> children = all.stream().filter(f -> f.get("pid").equals(p.get("id"))).collect(Collectors.toList());
            if (!children.isEmpty()) {
                treeData(menuNode.children, children, all);
            } else {

            }
            menuNodes.add(menuNode);
//            list.add(new Object() {
//                Map<String, Object> menuNode = p;
//                List<Map<String, Object>> child = children;
//            });
        });
//        return list;
    }
}
