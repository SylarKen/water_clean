package com.sylar.water_clean.models;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：Sylar
 * @date ：Created in 2019/11/20 15:51
 * @description：
 * @modified By：
 * @version: $
 */
@Data

public class Sender {
    public int id;
    public String CIMI;
    public String ICCID;

//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")  //FastJson包使用注解
    private Date time_create;

    public String battery;
    public int enable;

//    @JSONField(format = "yyyy-MM-dd")
    public Date time_last_active;

//    @JSONField(serialize=false)
    public BigDecimal value_last;
    public String data_last;
}
