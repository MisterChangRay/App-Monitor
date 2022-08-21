package com.github.misterchangra.appmonitor.base.command.result;

public class SystemInfoResut extends BaseCMDResult {
    private String totalMemUse;
    private String totalMem;
    private String threads;
    private String avgload;
    private String cpuUserUsage;
    private String cpuSysUsage;

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
}
