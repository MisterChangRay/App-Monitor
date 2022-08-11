package com.github.misterchangray.appmonitor.appmonitorclient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.misterchangra.appmonitor.base.consts.API;
import com.github.misterchangra.appmonitor.base.dto.message.ClientInfo;
import com.github.misterchangra.appmonitor.base.util.IPUtil;
import com.github.misterchangray.appmonitor.appmonitorclient.service.config.Configuration;
import com.github.misterchangray.appmonitor.appmonitorclient.service.request.ConfigInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ConfigSyncService {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private Configuration configuration;
    private long postClientInfoLastTime = System.currentTimeMillis();

    /**
     * 30s 自动同步一次配置请求
     */
    @Scheduled(cron = "*/30 * * * *")
    public void syncConfig() {
//        String clientIp = ip;
//        if(!StringUtils.hasLength(ip)) {
//            ip = IPUtil.getLocalAddress();
//        }
//
//        ClientInfo clientInfo = new ClientInfo(API.CLIENT_INFO.getType(), clientIp, name);
//        String param = null;
//        try {
//            param = objectMapper.writeValueAsString(clientInfo);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        String s = new ConfigInfoRequest().doRequest(API.CLIENT_INFO, param);
    }


    @Scheduled(cron = "*/30 * * * *")
    public void postClientInfo() {
        int ttl = 5 * 1000;
        if(System.currentTimeMillis() - postClientInfoLastTime < ttl) {
            return;
        }

        ClientInfo clientInfo = new ClientInfo(API.CLIENT_INFO.getType(), configuration.getClientIp(), configuration.getClientName());
        String param = null;
        try {
            param = objectMapper.writeValueAsString(clientInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        new ConfigInfoRequest(configuration.getServerIp(), configuration.getServerPort()).doRequest(API.CLIENT_INFO, param);
    }
}
