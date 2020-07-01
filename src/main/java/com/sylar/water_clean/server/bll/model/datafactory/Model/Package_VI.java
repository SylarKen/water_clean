package com.sylar.water_clean.server.bll.model.datafactory.Model;


import com.sylar.water_clean.server.bll.model.datafactory.DataPackage;
import com.sylar.water_clean.service.Project.Server.RecordService;
import com.sylar.water_clean.service.Project.Server.SenderService;
import com.sylar.water_clean.service.Project.Settings.StationService;
import com.sylar.water_clean.utils.ByteUtils;
import com.sylar.water_clean.utils.GatewayUtils;
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
public class Package_VI extends DataPackage {

    @Autowired
    StationService service_station;

    @Autowired
    RecordService service_record;

    public static Package_VI package_vi;

    @PostConstruct
    public void init() {
        package_vi = this;
        package_vi.service_station = this.service_station;
        package_vi.service_record = this.service_record;
    }

    //数据包信息
    public Byte[] Threshold;
    public double Current_A;
    public double Current_B;
    public double Current_C;
    public double Voltage_A;
    public double Voltage_B;
    public double Voltage_C;
    public String Alarm;
    public Date date;

    private Package_VI() {

    }

    public Package_VI(Byte[] dataPackage) throws ParseException {
        super(dataPackage);
        String message = "";
        this.Current_A = ByteUtils.bytesToInt_L(dataPackage[13], dataPackage[14]) / 10.0;
        this.Current_B = ByteUtils.bytesToInt_L(dataPackage[15], dataPackage[16]) / 10.0;
        this.Current_C = ByteUtils.bytesToInt_L(dataPackage[17], dataPackage[18]) / 10.0;
        this.Voltage_A = ByteUtils.bytesToInt_L(dataPackage[19], dataPackage[20]) / 10.0;
        this.Voltage_B = ByteUtils.bytesToInt_L(dataPackage[21], dataPackage[22]) / 10.0;
        this.Voltage_C = ByteUtils.bytesToInt_L(dataPackage[23], dataPackage[24]) / 10.0;
        this.Alarm = String.format("%8s", Integer.toBinaryString(dataPackage[25] & 0xFF)).replace(' ', '0') + String.format("%8s", Integer.toBinaryString(dataPackage[26] & 0xFF)).replace(' ', '0');
        Byte[] b = new Byte[6];
        System.arraycopy(dataPackage, 32, b, 0, b.length);
//        String[] label = new String[]{"-", "-", " ", ":", ":", ""};
//        StringBuilder date = new StringBuilder();
//        date.append("20");
//        for (int i = 32, j = 0; i < 38; i++, j++) {
//            String s = String.format("%02d", dataPackage[i] & 0xff).toUpperCase();
//            date.append(s).append(label[j]);
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = GatewayUtils.Bytes2Date(b);
        this.Response = GetResponse();
    }

    public Byte[] GetResponse() {
        Byte[] bytes = new Byte[0];
        //业务逻辑
        //对于心跳包，逻辑处理更新对应的工控机在数据库中的配置、活动时间
        try {
            List<Map<String, Object>> station = package_vi.service_station.GetStation_ByIMEI(this.IMEI);
            if (station.size() == 1) {
                Map<String, Object> params = new HashMap<>();
                params.put("station_id", station.get(0).get("id").toString());
                params.put("IMEI", this.IMEI);
                params.put("device_number", this.Address);
                params.put("current_a", this.Current_A);
                params.put("current_b", this.Current_B);
                params.put("current_c", this.Current_C);
                params.put("voltage_a", this.Voltage_A);
                params.put("voltage_b", this.Voltage_B);
                params.put("voltage_c", this.Voltage_C);
                params.put("alarm", this.Alarm);
                params.put("package", this.Data);
                params.put("time_client", this.date);
                Integer res = package_vi.service_record.Insert_VI(params);
                if (res > 0) {
                    System.out.println("VI 数据保存成功！");
                }
                try {
                    Integer res1 = package_vi.service_record.Update_Realtime_VI(params);
                    System.out.println("VI 实时数据更新成功！");
                }catch (Exception ee){
                    System.out.println("VI 实时数据更新时发生异常！");
                }
//            SenderService sv = new SenderService();
//            int res1 = sv.updateSenderStatus(params);
//            int res = senderService.updateSenderStatus(params);
            } else if (station.size() == 0) {
                System.out.println("站点未添加");
            }else {
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
