package com.sylar.water_clean.models;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：Sylar
 * @date ：Created in 2019/8/5 14:44
 * @description：
 * @modified By：
 * @version: $
 */
public class Menu_Node implements Serializable {
    public String nodeName;
    public String nodeCode;
    public String url;
    public String icon;
    public List<Menu_Node> children;
}
