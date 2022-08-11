package com.github.misterchangra.appmonitor.base.dto.message;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

@MagicClass
public class ClientInfo {
    @MagicField(order = 1)
    private byte messageType;
    @MagicField(order = 6, size = 15)
    private String ip;
    @MagicField(order = 9, size = 300)
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientInfo(byte messageType, String ip, String name) {
        this.messageType = messageType;
        this.ip = ip;
        this.name = name;
    }

    public ClientInfo() {

    }
}
