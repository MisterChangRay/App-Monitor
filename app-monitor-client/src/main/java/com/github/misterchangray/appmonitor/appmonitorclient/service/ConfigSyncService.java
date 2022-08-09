package com.github.misterchangray.appmonitor.appmonitorclient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.misterchangra.appmonitor.base.consts.API;
import com.github.misterchangra.appmonitor.base.dto.message.ClientInfo;
import com.github.misterchangra.appmonitor.base.dto.message.ProcessInfo;
import com.github.misterchangra.appmonitor.base.util.IPUtil;
import com.github.misterchangray.appmonitor.appmonitorclient.service.request.ConfigInfoRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ConfigSyncService {
    private static volatile String serverIp;
    private static volatile int serverPort;
    private List<ProcessInfo> processInfos;
    @Value("${client.ip:}")
    private String ip;
    @Value("${client.name}")
    private String name;
    ObjectMapper objectMapper = new ObjectMapper();


    public void syncConfig() {
        String clientIp = ip;
        if(!StringUtils.hasLength(ip)) {
            ip = IPUtil.getLocalAddress();
        }

        ClientInfo clientInfo = new ClientInfo(API.CLIENT_INFO.getType(), clientIp, name);
        String param = null;
        try {
            param = objectMapper.writeValueAsString(clientInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String s = new ConfigInfoRequest().doRequest(API.CLIENT_INFO, param);
    }
}
