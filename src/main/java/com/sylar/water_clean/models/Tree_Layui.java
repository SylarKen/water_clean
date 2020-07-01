package com.sylar.water_clean.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author ：Sylar
 * @date ：Created in 2019/12/2 14:28
 * @description：
 * @modified By：
 * @version: $
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Tree_Layui extends ArrayList<Tree_Node_Layui> implements Serializable {
//    public List<Tree_Node_Layui> nodes;

    public Tree_Layui() {


    }

//    public boolean add(Tree_Node_Layui menuNode) {
//        this.add(menuNode);
//        return true;
//    }
//    public Tree_Layui(List<Map<String, Object>> top, List<Map<String, Object>> children) {
//
//    }
}
