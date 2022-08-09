package com.github.misterchangra.appmonitor.base.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPUtil {
        public static void sendUDP(String ip, int port, byte[] data) throws IOException {
            //创建InetAddress对象,封装自己的IP地址
            InetAddress inet = InetAddress.getByName(ip);
            DatagramPacket dp = new DatagramPacket(data,data.length,inet,port);
            //创建DatagramSocket对象,数据包的发送和接收对象
            DatagramSocket ds = new DatagramSocket();
            //调用ds对象的方法send,发送数据包
            ds.send(dp);
            ds.close();
        }

}
