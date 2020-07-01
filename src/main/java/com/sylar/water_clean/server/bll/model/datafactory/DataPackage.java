package com.sylar.water_clean.server.bll.model.datafactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: weightwebservice
 * @description: TCP 数据中的数据部分
 * @author: johnny
 * @create: 2018-07-21 09:39
 **/
public abstract class DataPackage {
    public String Protocol;
    public String IMEI;
    public String Order;
    public String Type;
    public String Address;
    public String Data;
    public Byte[] Response;


    public DataPackage(Byte[] buff) {
        List<String> buff_str = new ArrayList<String>();
        StringBuilder str = new StringBuilder();
        for (byte b : buff) {
            String s = String.format("%1$02x", b & 0xff).toUpperCase();
            buff_str.add(s);
            str.append(s).append(" ");
        }

        this.Protocol = buff_str.get(10) + buff_str.get(11);
        StringBuilder IMEI = new StringBuilder();
        for (int i = 1; i < 1 + 8; i++) {
            IMEI.append(buff_str.get(i));
        }
        this.IMEI = IMEI.toString();
        this.Order = buff_str.get(9);
        this.Type = buff_str.get(10) + buff_str.get(11);
        this.Address = buff_str.get(12);
        this.Data = str.toString();

    }

    protected DataPackage() {
    }

    abstract public Byte[] GetResponse() throws Exception;
}
