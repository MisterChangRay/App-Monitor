package com.github.misterchangra.appmonitor.base.dto.message;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

public class ClientInfo {
    private String ip;
    private String name;
    // 通过配置文件更新时间决定是否重新下发配置
    private long configSign;
    private int threadsCount;
    private int processCount;
    private int memUsed;
    private List<String> warning;
    private List<MonitorProcessExtractInfo> monitorProcessExtractInfos;

    public long getConfigSign() {
        return configSign;
    }

    public void setConfigSign(long configSign) {
        this.configSign = configSign;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientInfo(String ip, String name, long configSign) {
        this.ip = ip;
        this.name = name;
        this.configSign = configSign;
    }

    public int getThreadsCount() {
        return threadsCount;
    }

    public void setThreadsCount(int threadsCount) {
        this.threadsCount = threadsCount;
    }

    public int getProcessCount() {
        return processCount;
    }

    public void setProcessCount(int processCount) {
        this.processCount = processCount;
    }

    public int getMemUsed() {
        return memUsed;
    }

    public void setMemUsed(int memUsed) {
        this.memUsed = memUsed;
    }

    public List<String> getWarning() {
        return warning;
    }

    public void setWarning(List<String> warning) {
        this.warning = warning;
    }

    public List<MonitorProcessExtractInfo> getMonitorProcessExtractInfos() {
        return monitorProcessExtractInfos;
    }

    public void setMonitorProcessExtractInfos(List<MonitorProcessExtractInfo> monitorProcessExtractInfos) {
        this.monitorProcessExtractInfos = monitorProcessExtractInfos;
    }

    public ClientInfo() {

    }
}
