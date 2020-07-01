package com.sylar.water_clean.server.bll.model.datafactory;

import com.sylar.water_clean.server.bll.model.datafactory.Model.Package_IO;
import com.sylar.water_clean.server.bll.model.datafactory.Model.Package_VI;

import java.util.Iterator;
import java.util.List;

/**
 * @program: weightwebservice
 * @description: Tcp 数据处理类
 * @author: johnny
 * @create: 2018-07-20 17:16
 **/
public class DataTools {

    private void DataTools() throws Exception {

    }

    public static Byte[] ByteListTobyteArray(List<Byte> list)
    {
        Byte[] bytes = new Byte[list.size()];

        int i = 0;
        Iterator<Byte> iterator = list.iterator();
        while (iterator.hasNext()) {
            bytes[i] = iterator.next();
            i++;
        }
        return bytes;

    }

    static DataPackage DataProcessIncome(String protocol, Byte[] dataPackageBuff) throws Exception {

        DataPackage dataPackage = null;
        //根据不同的协议号 protocal 进入不同的处理方法
        switch (protocol) {
            case "0101": {
                dataPackage = new Package_VI(dataPackageBuff);

            }
            break;
            case "0102": {
                dataPackage = new Package_IO(dataPackageBuff);
            }
            break;
            case "3": {

            }
            break;
            case "4": {

            }
            break;
            default: {

            }


        }

        return dataPackage;
    }
}
