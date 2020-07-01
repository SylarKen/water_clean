package com.sylar.water_clean.server.bll.model.datafactory.Model;


import com.sylar.water_clean.server.bll.model.datafactory.DataPackage;
import com.sylar.water_clean.service.Project.Server.RecordService;
import com.sylar.water_clean.service.Project.Settings.StationService;
import com.sylar.water_clean.utils.ByteUtils;
import com.sylar.water_clean.utils.GatewayUtils;
import com.sylar.water_clean.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program:
 * @description: 电流电压数据包类，继承于DataPackage
 * @author: johnny
 * @create: 2018-07-19 10:49
 **/
@Component
public class Package_IO extends DataPackage {

    @Autowired
    StationService service_station;

    @Autowired
    RecordService service_record;

    public static Package_IO package_io;

    @PostConstruct
    public void init() {
        package_io = this;
        package_io.service_station = this.service_station;
        package_io.service_record = this.service_record;
    }

    //数据包信息
    public Byte[] Threshold;
    public String io;

    public Date date;

    private Package_IO() {

    }

    public Package_IO(Byte[] dataPackage) throws ParseException {
        super(dataPackage);
        String message = "";
        Integer io_int = ByteUtils.bytesToInt_L(dataPackage[13], dataPackage[14]);
//        this.io = (String.format("%8s", Integer.toBinaryString(dataPackage[14] & 0xFF)).replace(' ', '0')+String.format("%8s", Integer.toBinaryString(dataPackage[13] & 0xFF)).replace(' ', '0'));
//        String io_str = (String.format("%8s", Integer.toBinaryString(dataPackage[13] & 0xFF)).replace(' ', '0') + String.format("%8s", Integer.toBinaryString(dataPackage[14] & 0xFF)).replace(' ', '0'));
        this.io = String.format("%16s", Integer.toBinaryString(io_int & 0xFFFF)).replace(' ', '0');
        Byte[] b = new Byte[6];
        System.arraycopy(dataPackage, 22, b, 0, b.length);
        this.date = GatewayUtils.Bytes2Date(b);
        this.Response = GetResponse();
    }

    public Byte[] GetResponse() {
        Byte[] bytes = new Byte[0];
        //业务逻辑
        //对于心跳包，逻辑处理更新对应的工控机在数据库中的配置、活动时间
        try {
            List<Map<String, Object>> station = package_io.service_station.GetStation_ByIMEI(this.IMEI);
            if (station.size() == 1) {
                Map<String, Object> params = new HashMap<>();
                params.put("station_id", station.get(0).get("id").toString());
                params.put("IMEI", this.IMEI);
                params.put("device_number", this.Address);
                params.put("io", this.io);
                params.put("package", this.Data);
                params.put("time_client", this.date);
                Integer res = package_io.service_record.Insert_IO(params);
                if (res > 0) {
                    System.out.println("IO 数据保存成功！");
                }
                try {
                    String io_str = StringUtils.Reverse(this.io);
                    Map<String, Object> params_realtime = new HashMap<>();
                    params_realtime.put("station_id", station.get(0).get("id").toString());
                    params_realtime.put("IMEI", this.IMEI);
                    params_realtime.put("device_number", this.Address);
                    params_realtime.put("io1", io_str.charAt(0));
                    params_realtime.put("io2", io_str.charAt(1));
                    params_realtime.put("io3", io_str.charAt(2));
                    params_realtime.put("io4", io_str.charAt(3));
                    params_realtime.put("io5", io_str.charAt(4));
                    params_realtime.put("io6", io_str.charAt(5));
                    params_realtime.put("io7", io_str.charAt(6));
                    params_realtime.put("io8", io_str.charAt(7));
                    params_realtime.put("io9", io_str.charAt(8));
                    Integer res1 = package_io.service_record.Update_Realtime_IO(params_realtime);
                    System.out.println("IO 实时数据更新成功！");
                } catch (Exception ee) {
                    System.out.println("IO 实时数据更新时发生异常！");
                }
            } else if (station.size() == 0) {
                System.out.println("站点未添加");
            } else {
                System.out.println("站点数量异常");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //更新成功
        if (true) {


        } else   //否则
        {


        }


        return bytes;
    }
}
