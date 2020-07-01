package com.sylar.water_clean.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：Sylar
 * @date ：Created in 2019/8/5 14:40
 * @description：
 * @modified By：
 * @version: $
 */
public class Menu implements Serializable {
    public List<Menu_Node> nodes;
    public List<Map<String, Object>> menus_all;
    public Menu (){
        nodes = new ArrayList<>() ;

    }
    public void add(Menu_Node menuNode)     {
        this.nodes.add(menuNode);
    }
}
