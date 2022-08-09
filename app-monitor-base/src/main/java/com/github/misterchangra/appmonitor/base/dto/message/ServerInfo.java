package com.github.misterchangra.appmonitor.base.dto.message;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass
public class ServerInfo {
    @MagicField(order = 1)
    private byte messageType;
    @MagicField(order = 3, size = 15)
    private String ip;
    @MagicField(order = 6)
    private int port;

    public ServerInfo(byte messageType, String ip, int port) {
        this.messageType = messageType;
        this.port = port;
        this.ip = ip;

    }

    public ServerInfo(byte messageType) {
        this.messageType = messageType;
    }

    public byte getMessageType() {
        return messageType;
    }

    public void setMessageType(byte messageType) {
        this.messageType = messageType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
