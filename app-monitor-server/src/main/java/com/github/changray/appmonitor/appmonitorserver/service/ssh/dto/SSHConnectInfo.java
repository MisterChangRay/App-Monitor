package com.github.changray.appmonitor.appmonitorserver.service.ssh.dto;

import com.jcraft.jsch.Session;

public class SSHConnectInfo extends SSHConfig {
    private boolean success;
    private String desc;

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
