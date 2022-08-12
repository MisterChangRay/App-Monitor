package com.github.misterchangra.appmonitor.base.util;

import com.github.misterchangra.appmonitor.base.command.FindInProcessCMD;
import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class UDPUtil {

    public static void main(String[] args) {
        List<FindInProcessCMDResult> java = new FindInProcessCMD().execCmd("java").getResult();

        System.out.println(22);
    }
        public static void sendUDP(String ip, int port, byte[] data) throws IOException {
            //创建InetAddress对象,封装自己的IP地址
            InetAddress inet = InetAddress.getByName(ip);
            DatagramPacket dp = new DatagramPacket(data,data.length,inet,port);
            //创建DatagramSocket对象,数据包的发送和接收对象
            DatagramSocket ds = new DatagramSocket();
            ds.setReuseAddress(false);
            //调用ds对象的方法send,发送数据包
            ds.send(dp);
            ds.close();
        }

}
