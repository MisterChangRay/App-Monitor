package com.github.misterchangray.appmonitor.appmonitorclient.service.config;

import com.github.misterchangra.appmonitor.base.dto.message.MonitorProcessInfo;
import com.github.misterchangra.appmonitor.base.util.IPUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private volatile long configSign = 0;
    private CopyOnWriteArrayList<String> warningMsgQueue = new CopyOnWriteArrayList<>();

    public CopyOnWriteArrayList<String> getWarningMsgQueue() {
        return warningMsgQueue;
    }

    public void setWarningMsgQueue(CopyOnWriteArrayList<String> warningMsgQueue) {
        this.warningMsgQueue = warningMsgQueue;
    }

    public long getConfigSign() {
        return configSign;
    }

    public void setConfigSign(long configSign) {
        this.configSign = configSign;
    }

    public boolean hasServerConfig() {
        return Objects.nonNull(serverIp) && Objects.nonNull(serverPort);
    }
    private List<MonitorProcessInfo> processInfos = new ArrayList<>();

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
