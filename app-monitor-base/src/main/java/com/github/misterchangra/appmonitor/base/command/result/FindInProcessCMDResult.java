package com.github.misterchangra.appmonitor.base.command.result;

public class FindInProcessCMDResult extends BaseCMDResult{
    private String name;
    private String pid;
    private String memUseKB;

    public FindInProcessCMDResult(String name, String pid, String memUse) {
        this.name = name;
        this.pid = pid;
        this.memUseKB = memUse;
    }

    public FindInProcessCMDResult() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMemUseKB() {
        return memUseKB;
    }

    public void setMemUseKB(String memUseKB) {
        this.memUseKB = memUseKB;
    }
}
