package com.github.misterchangray.appmonitor.appmonitorclient.service.config;

import com.github.misterchangra.appmonitor.base.dto.message.MonitorProcessInfo;
import com.github.misterchangra.appmonitor.base.util.IPUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@org.springframework.context.annotation.Configuration()
public class Configuration {
    @Value("${monitor.serverIp:}")
    public String serverIp;
    @Value("${monitor.serverPort:}")
    public Integer serverPort;
    @Value("${client.ip:}")
    private String clientIp;
    @Value("${client.name}")
    private String clientName;
    @Value("${client.udpPort:23670}")
    private int udpPort;

    private List<MonitorProcessInfo> processInfos;

    public List<MonitorProcessInfo> getProcessInfos() {
        return processInfos;
    }

    public void setProcessInfos(List<MonitorProcessInfo> processInfos) {
        this.processInfos = processInfos;
    }

    public String getClientIp() {
        if(StringUtils.hasLength(clientIp)) {
            return clientIp;
        }
        clientIp = IPUtil.getLocalAddress();
        return clientIp;
    }

    public String getClientName() {
        return clientName;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public  String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }
}
