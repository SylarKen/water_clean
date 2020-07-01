package com.sylar.water_clean.server.bll.model.datafactory;

import com.sylar.water_clean.service.Project.Server.RecordService;
import com.sylar.water_clean.service.Project.Server.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;


/**
 * @program: weightwebservice
 * @description: 工控机上传的数据包类
 * @author: johnny
 * @create: 2018-07-18 16:45
 **/
@Component
public class TCPPackage {
    @Autowired
    RecordService service_record;

    @Autowired
    SenderService service_sender;

    public static TCPPackage tcpPackage;

    @PostConstruct
    public void init() {
        tcpPackage = this;
        tcpPackage.service_record = this.service_record;
        tcpPackage.service_sender = this.service_sender;
    }

    public String Header;
    public int PackageSize;
    public String Protocol;
    public String IMEI;
    public Byte[] Data;
//    public String Date;
//    public String Alarm;

    public String Ender;
    public Byte[] Buff;
    public DataPackage DataPackage;
    public Byte[] Response;


    private TCPPackage() throws Exception {


    }

    public TCPPackage(Byte[] buff) throws Exception {
//        service = (RecordService) SpringContextUtil.getBean("service");
        if (Init(buff)) {
//            this.Save();
        }

    }

    private boolean Init(Byte[] buff) throws Exception {
        int buffLength = buff.length;
        if (buffLength > 20) {
            List<String> buff_str = new ArrayList<String>();
            for (byte b : buff) {
                buff_str.add(String.format("%1$02x", b & 0xff).toUpperCase());
            }

            //数据包头有效
            if (buff_str.get(0).equals("A5") && buff_str.get(1).equals("5A")) {
                this.Buff = buff;
                this.Header = buff_str.get(0) + buff_str.get(1);
                this.PackageSize = buff[2] & 0xff;
                this.Protocol = buff_str.get(13) + buff_str.get(14);
                String IMEI = "";
                for (int i = 4; i < 4 + 8; i++) {
                    IMEI += buff_str.get(i);
                }
                this.IMEI = IMEI;
                List<Byte> data = new ArrayList<>(Arrays.asList(buff).subList(3, 3 + this.PackageSize));
                this.Data = data.toArray(new Byte[data.size()]);


                Byte[] dataPackage_byte = new Byte[this.Data.length];
                System.arraycopy(this.Data, 0, dataPackage_byte, 0, dataPackage_byte.length);
                this.DataPackage = DataTools.DataProcessIncome(this.Protocol, dataPackage_byte);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private Byte[] GetResponse(byte[] dataResponse) throws Exception {
        //调用业务逻辑

        ArrayList<Byte> resArr = new ArrayList();
//        List<Byte> resArr = new ArrayList();
        if (this.Buff != null && this.Buff.length >= 8) {
            for (int i = 0; i < 8; i++) {
                resArr.add(new Byte(this.Buff[i]));
            }
        }
        if (dataResponse != null && dataResponse.length > 0) {
            for (int i = 0; i < 8; i++) {
                resArr.add(new Byte(dataResponse[i]));
            }
        }

        if (this.Buff != null) {
            if (this.Buff.length >= 8) {
                for (int i = this.Buff.length - 3; i < this.Buff.length; i++) {
                    resArr.add(new Byte(this.Buff[i]));
                }
            }
        }

        //根据业务逻辑反馈的结果生成响应数据
        Byte[] bytes = DataTools.ByteListTobyteArray(resArr);
//        byte[] bytes = new byte[resArr.size()];
//
//        int i = 0;
//        Iterator<Byte> iterator = resArr.iterator();
//        while (iterator.hasNext()) {
//            bytes[i] = iterator.next();
//            i++;
//        }
        return bytes;
//        res = new Response();
//        res.Allow = true;
    }

//    private boolean Save() {
//        try {
//            String str = "";
//            for (int i = 0; i < this.Buff.length; i++) {
//                String s = String.format("%1$02x", this.Buff[i] & 0xff).toUpperCase();
//                str += s + " ";
//            }
////            if (tcpPackage.service_sender.CheckSender(this.ICCID)) {
//            Map<String, Object> params_check = new HashMap<>();
//            params_check.put("locationId", this.IMEI);
//            if (tcpPackage.service_record.CheckBind(params_check)) {
//                Map<String, Object> params = new HashMap<>();
//                params.put("bindRecordID", 0);
//                params.put("IMEI", this.IMEI);
//                params.put("Latitude_GCJ", this.Latitude_GCJ);
//                params.put("Longitude_GCJ", this.Longitude_GCJ);
//                params.put("Latitude_BD", this.Latitude_BD);
//                params.put("Longitude_BD", this.Longitude_BD);
//                params.put("datapackage", str);
//
////                tcpPackage.service_sender.Insert(params);
//                if (tcpPackage.service_record.Insert(params) > 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } else {
//                return false;
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//
//
//    }
}
