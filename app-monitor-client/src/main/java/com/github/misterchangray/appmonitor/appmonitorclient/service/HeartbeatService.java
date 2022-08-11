package com.github.misterchangray.appmonitor.appmonitorclient.service;

import com.github.misterchangra.appmonitor.base.consts.API;
import com.github.misterchangra.appmonitor.base.dto.message.ClientInfo;
import com.github.misterchangra.appmonitor.base.dto.message.ServerInfo;
import com.github.misterchangra.appmonitor.base.util.UDPServer;
import com.github.misterchangray.appmonitor.appmonitorclient.service.config.Configuration;
import com.github.misterchangray.core.MagicByte;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@Service
public class HeartbeatService implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @PostConstruct
    public void start() {
        Configuration config = applicationContext.getBean(Configuration.class);

        UDPServer udpServer = new UDPServer(config.getUdpPort(), HeartbeatService::handle);
        udpServer.start();
    }

    public static void handle(byte[] data) {
        if(data[0] != API.SERVER_INFO.getType()) {
            return ;
        }
        ServerInfo pack = MagicByte.pack(data, ServerInfo.class);
        Configuration config = applicationContext.getBean(Configuration.class);
        if(!StringUtils.hasLength(config.getServerIp())) {
            config.setServerIp(pack.getIp());
            config.setServerPort(pack.getPort());
        }

        ConfigSyncService configSyncService = applicationContext.getBean(ConfigSyncService.class);
        configSyncService.postClientInfo();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        HeartbeatService.applicationContext = applicationContext;
    }
}
