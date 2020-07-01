package com.sylar.water_clean.server.bll.listener;


import com.sylar.water_clean.server.bll.model.datafactory.DataTools;
import com.sylar.water_clean.server.bll.model.datafactory.TCPPackage;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @program: weightwebservice
 * @description: Socket Thread 服务器线程处理类
 * @author: johnny
 * @create: 2018-07-13 14:11
 **/
public class ServerThread extends Thread {
    // 和本线程相关的Socket
    Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    //线程执行的操作，响应客户端的请求
    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        try {
            //把socket存入当前连接Map中

//            TCPServer.CurrentConnections.put(socket.getInetAddress().getHostAddress() + ":" + socket.getPort(), (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
            TCPServer.CurrentConnections.put(socket.getInetAddress().getHostAddress() + ":" + socket.getPort(), "");
            System.out.println(TCPServer.CurrentConnections);
            //获取输入流，并读取客户端信息
            is = socket.getInputStream();
//            isr = new InputStreamReader(is, "ASCII");
//            br = new BufferedReader(isr);
            String info = null;
            int res = -1;
            Byte[] buffer;
            ArrayList<Byte> buffer_list = new ArrayList<>();
            byte[] buffer_rec = new byte[1024];
//            while ((info = br.readLine()) != null) {//循环读取客户端的信息
//循环读取客户端的信息
            while ((res = is.read(buffer_rec)) > 0) {
                for (int i = 0; i < res; i++) {
                    buffer_list.add(buffer_rec[i]);
                }
                System.out.println(res);
                if (is.available() > 0) {
                    continue;
                }
                buffer = DataTools.ByteListTobyteArray(buffer_list);
                int tt = buffer_list.indexOf(new Byte[]{0x74, 0x74});
                int t = Arrays.binarySearch(buffer, 0, buffer.length - 1, (byte) 104);

                int startIndex = 0;
                int endIndex = buffer.length;
                ArrayList<Byte[]> buf_List = new ArrayList<>();
                while (startIndex < endIndex - 1) {
                    ArrayList<Byte> buf_l = new ArrayList<>();
                    boolean cut = false;
                    buf_l.add(buffer[startIndex]);
                    for (int i = startIndex + 1; i < endIndex; i++) {
                        if ((buffer[i - 1] & 0xff) == 0x55 && (buffer[i] & 0xff) == 0xAA) {
                            buf_l.add(buffer[i]);

                            buf_List.add(DataTools.ByteListTobyteArray(buf_l));
                            cut = true;
                            startIndex = i + 1;
                            break;
                        } else {
                            startIndex++;
                            buf_l.add(buffer[i]);
                        }

                    }
                    if (!cut) {
                        buf_List.add(DataTools.ByteListTobyteArray(buf_l));
                    }
                }


                String str = String.valueOf(res) + " ";
                List<String> buff_list = new ArrayList<>();
                for (Integer i = 0; i < res; i++) {
                    String s = String.format("%1$02x", buffer[i] & 0xff).toUpperCase();
                    buff_list.add(s);
                    str += String.format("%1$02x", buffer[i] & 0xff).toUpperCase() + " ";

                }
                String[] buff_arr = new String[buff_list.size()];
                buff_list.toArray(buff_arr);

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                System.out.println("当前时间：" + df.format(new Date()));
                System.out.println("我是服务器，客户端说：" + str);
                try {
                    for (Byte[] b : buf_List) {
                        Byte[] buff = new Byte[b.length];
                        System.arraycopy(buffer, 0, buff, 0, b.length);
                        TCPPackage tcpPackage = new TCPPackage(b);
                        TCPServer.CurrentConnections.put(socket.getInetAddress().getHostAddress() + ":" + socket.getPort(), (tcpPackage != null && tcpPackage.IMEI != null) ? tcpPackage.IMEI : "");
                        String response = "";
                        if (tcpPackage.Response != null) {
                            for (Integer i = 0; i < tcpPackage.Response.length; i++) {
                                String s = String.format("%1$02x", tcpPackage.Response[i] & 0xff).toUpperCase();
                                response += s;
                            }
                            //输出响应结果
                            try {

                                DataOutputStream out = new DataOutputStream(os);
                                byte[] out_buff = new byte[tcpPackage.Response.length];
                                System.arraycopy(tcpPackage.Response, 0, out_buff, 0, tcpPackage.Response.length);
                                out.write(out_buff);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        System.out.println("我是服务器，客户端的响应是：" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                buffer_list = new ArrayList<>();

            }


//            while ((res = is.read(buffer)) != -1) {//循环读取客户端的信息
//                String str = String.valueOf(res) + " ";
//                List<String> buff_list = new ArrayList<>();
//                for (Integer i = 0; i < res; i++) {
////                    String s = Integer.toHexString(buff[i] & 0xff);
//                    String s = String.format("%1$02x", buffer[i] & 0xff).toUpperCase();
//                    buff_list.add(s);
//                    str += String.format("%1$02x", buffer[i] & 0xff).toUpperCase() + " ";//Integer.toHexString(buff[i] & 0xff)) + " ";//Integer.valueOf(Integer.toString( buff[i]));
//
//                }
//                String[] buff_arr = new String[buff_list.size()];
//                buff_list.toArray(buff_arr);
//                System.out.println("我是服务器，客户端说：" + str);
//                try {
//                    byte[] buff = new byte[res];
//                    System.arraycopy(buffer, 0, buff, 0, res);
//                    TCPPackage tcpPackage = new TCPPackage(buff);
//                    TCPServer.CurrentConnections.put(socket.getInetAddress().getHostAddress() + ":" + socket.getPort(), (tcpPackage != null && tcpPackage.ClientID != null) ? tcpPackage.ClientID : "");
//                    String response = "";
//                    if (tcpPackage.Response != null) {
//                        for (Integer i = 0; i < tcpPackage.Response.length; i++) {
//                            String s = String.format("%1$02x", tcpPackage.Response[i] & 0xff).toUpperCase();
//                            response += s;
//                        }
//                    }
//                    System.out.println("我是服务器，客户端的响应是：" + response);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
////                os = socket.getOutputStream();
////                PrintStream out = new PrintStream(os);
////                String out_str = "你好1231LKfjalsdfj/n/r";
////                byte[] out_buff=out_str.getBytes("UTF-8");
////                out.print(out_buff);
////                out.flush();
//
////                pw=new PrintWriter(new OutputStreamWriter(os,"GB18030"));//将输出流包装为打印流
////                pw.write("用户名：whf;密码：789");
////                pw.flush();
//
////                DataOutputStream out = new DataOutputStream(os);
////                byte[] out_buff = new byte[res];
////                System.arraycopy(buff, 0, out_buff, 0, res);
////                out.write(out_buff);
//
////                try {
////                    Socket socket1 = new Socket("192.168.9.202", 21001);
////                    os = socket1.getOutputStream();
////                    DataOutputStream out = new DataOutputStream(os);
////                    byte[] out_buff = new byte[res];
////                    System.arraycopy(buff, 0, out_buff, 0, res);
////                    out.write(out_buff);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//
//                buffer = new byte[1024];
//            }
            System.out.println("我是服务器，客户端说：接收完毕 " + socket.getInetAddress().getHostAddress());
            socket.shutdownInput();//关闭输入流
            //获取输出流，响应客户端的请求

            socket.shutdownOutput();//关闭输出流
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                TCPServer.CurrentConnections.remove(socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                if (pw != null)
                    pw.close();
                if (os != null)
                    os.close();
                if (br != null)
                    br.close();
                if (isr != null)
                    isr.close();
                if (is != null)
                    is.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
