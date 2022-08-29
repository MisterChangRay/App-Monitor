package com.github.changray.appmonitor.appmonitorserver.service.ssh.dto;

import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;

public class SSHExecuteInfo extends ServerInfo {
    private String command;
    private boolean success;
    private String desc;
    private String result;

    public static SSHExecuteInfo build(String cmd) {
        SSHExecuteInfo sshExecuteInfo= new SSHExecuteInfo();
        sshExecuteInfo.setCommand(cmd);
        return sshExecuteInfo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
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