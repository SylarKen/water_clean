package com.sylar.water_clean.models;

import lombok.Data;

import java.util.Date;

/**
 * @author ：Sylar
 * @date ：Created in 2019/10/16 17:25
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class Role {
    private Integer id;
    private String name;
    private String key;

    private String creator;//创建人
    private Date createTime;//创建时间
    private String editor;//修改人
    private Date editTime;//修改时间
    private Integer delete_flag = 0;//是否可用
}
