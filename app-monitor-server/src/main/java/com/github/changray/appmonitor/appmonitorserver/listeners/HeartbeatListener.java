package com.github.changray.appmonitor.appmonitorserver.listeners;

import com.github.changray.appmonitor.appmonitorserver.events.HeartBeatEvent;
import com.github.misterchangra.appmonitor.base.consts.API;
import com.github.misterchangra.appmonitor.base.dto.message.ServerInfo;
import com.github.misterchangra.appmonitor.base.util.IPUtil;
import com.github.misterchangra.appmonitor.base.util.UDPUtil;
import com.github.misterchangray.core.MagicByte;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HeartbeatListener {
    private static int udpPort;
    private static String ip;
    private static int httpPort;
    /**
     * 每5秒广播一次服务器信息
     */
    @EventListener(value = {HeartBeatEvent.class})
    public void broadcast() {
        ServerInfo serverInfo = new ServerInfo(API.SERVER_INFO.getType(), ip, httpPort);
        byte[] bytes = MagicByte.unpackToByte(serverInfo);
        try {
            UDPUtil.sendUDP(IPUtil.BROADCAST_ADDRESS, udpPort, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Value("${client.udpPort:23670}")
    public  void setUdpPort(int udpPort) {
        HeartbeatListener.udpPort = udpPort;
    }

    @Value("${server.port:}")
    public  void setHttpPort(int httpPort) {
        HeartbeatListener.httpPort = httpPort;
    }

    @Value("${server.ip:}")
    public  void setIp(String ip) {
        if(ip.length() == 0) {
            HeartbeatListener.ip = IPUtil.getLocalAddress();
            return;
        }
        HeartbeatListener.ip = ip;
    }
}
