package com.github.misterchangra.appmonitor.base.command;

public class BaseCMDResult {
    private StringBuilder data;
    private boolean success;
    private String desc;

    public BaseCMDResult() {
    }

    public BaseCMDResult(StringBuilder data, boolean success, String desc) {
        this.data = data;
        this.success = success;
        this.desc = desc;
    }

    public StringBuilder getData() {
        return data;
    }

    public void setData(StringBuilder data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
