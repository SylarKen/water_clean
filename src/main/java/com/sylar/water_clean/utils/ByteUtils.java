package com.sylar.water_clean.utils;

/**
 * @author ：Sylar
 * @date ：Created in 2020/1/3 15:28
 * @description：二进制、十进制、十六进制转换
 * @modified By：
 * @version: $
 */
public class ByteUtils {
    // Byte[] 转换为字符串，数组高位在前  —— 如：{00, 59} - 22784
    static public Integer bytesToInt_R(Byte a, Byte... b) {
        int dec = a;
        for (int i = 0; i < b.length; i++) {
            dec = dec | b[i] << (8 * (i + 1));
        }
        return dec;

    }

    // Byte[] 转换为Int，数组低位在前 —— 如：{00, 59} - 89
    static public Integer bytesToInt_L(Byte a, Byte... b) {
        int dec = 0;
        for (int i = b.length - 1; i >= 0; i--) {
            dec = dec | (b[i] & 0xff) << (8 * (b.length - 1 - i));
        }
        dec = dec | (a & 0xff) << (8 * (b.length));
//        int dec = a;
//        for (int i = 0; i < b.length; i++) {
//            dec = dec | b[i] << (8 * (i + 1));
//        }
        return dec;

    }
}
