package com.github.misterchangray.appmonitor.appmonitorclient.service;

import com.github.misterchangra.appmonitor.base.consts.API;
import com.github.misterchangra.appmonitor.base.dto.message.ServerInfo;
import com.github.misterchangra.appmonitor.base.util.UDPServer;
import com.github.misterchangray.core.MagicByte;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UDPBroadcastService {
    @Value("${client.ip}")
    private String ip;
    @Value("${client.name}")
    private String name;
    @Value("${client.udpPort:23670}")
    private int udpPort;


    @PostConstruct
    public void start() {
        UDPServer udpServer = new UDPServer(udpPort, UDPBroadcastService::handle);
        udpServer.start();
    }

    public static byte[] handle(byte[] data) {
        if(data[0] != API.SERVER_INFO.getType()) {
            return null;
        }

        ServerInfo pack = MagicByte.pack(data, ServerInfo.class);
        System.out.println("收到消息" + pack.getIp() + ", port:" + pack.getPort());

        return new byte[0];
    }
}
