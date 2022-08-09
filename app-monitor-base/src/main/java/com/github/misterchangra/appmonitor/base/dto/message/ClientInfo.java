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


    public ClientInfo(byte messageType, String ip, String name) {
        this.messageType = messageType;
        this.ip = ip;
        this.name = name;
    }
}
