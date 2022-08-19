package com.github.misterchangra.appmonitor.base.dto.message;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

public class ClientInfo {
    private String ip;
    private String name;
    // 通过配置文件更新时间决定是否重新下发配置
    private long configSign;

    private String totalMemUse;
    private String totalMem;
    private String threads;
    private String avgload;
    private String cpuUserUsage;
    private String cpuSysUsage;


    private List<String> warning;
    private List<MonitorProcessExtractInfo> monitorProcessExtractInfos;

    public long getConfigSign() {
        return configSign;
    }

    public String getTotalMemUse() {
        return totalMemUse;
    }

    public void setTotalMemUse(String totalMemUse) {
        this.totalMemUse = totalMemUse;
    }

    public String getTotalMem() {
        return totalMem;
    }

    public void setTotalMem(String totalMem) {
        this.totalMem = totalMem;
    }

    public String getThreads() {
        return threads;
    }

    public void setThreads(String threads) {
        this.threads = threads;
    }

    public String getAvgload() {
        return avgload;
    }

    public void setAvgload(String avgload) {
        this.avgload = avgload;
    }

    public String getCpuUserUsage() {
        return cpuUserUsage;
    }

    public void setCpuUserUsage(String cpuUserUsage) {
        this.cpuUserUsage = cpuUserUsage;
    }

    public String getCpuSysUsage() {
        return cpuSysUsage;
    }

    public void setCpuSysUsage(String cpuSysUsage) {
        this.cpuSysUsage = cpuSysUsage;
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
