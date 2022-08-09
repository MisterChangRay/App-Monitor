package com.github.changray.appmonitor.appmonitorserver.service;

import com.github.misterchangra.appmonitor.base.consts.API;
import com.github.misterchangra.appmonitor.base.dto.message.ServerInfo;
import com.github.misterchangra.appmonitor.base.util.IPUtil;
import com.github.misterchangra.appmonitor.base.util.UDPUtil;
import com.github.misterchangray.core.MagicByte;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UDPBroadcastService {
    private static int udpPort;
    private static String ip;
    private static String httpPort;
    /**
     * 每5秒广播一次服务器信息
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void broadcast() {
        ServerInfo serverInfo = new ServerInfo(API.SERVER_INFO.getType(), ip, udpPort);
        byte[] bytes = MagicByte.unpackToByte(serverInfo);
        try {
            UDPUtil.sendUDP(IPUtil.BROADCAST_ADDRESS, udpPort, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Value("${client.udpPort:23670}")
    public  void setUdpPort(int udpPort) {
        UDPBroadcastService.udpPort = udpPort;
    }

    @Value("${server.port:}")
    public  void setHttpPort(String httpPort) {
        UDPBroadcastService.httpPort = httpPort;
    }

    @Value("${server.ip:}")
    public  void setIp(String ip) {
        if(ip.length() == 0) {
            UDPBroadcastService.ip = IPUtil.getLocalAddress();
            return;
        }
        UDPBroadcastService.ip = ip;
    }
}
