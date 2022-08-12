package com.github.misterchangra.appmonitor.base.consts;

public enum API {
    CLIENT_INFO(1, "/console/collect/clientinfo", "客户端基本信息"),
    SERVER_INFO(2, "", "服务端基本信息"),
    CONFIG_INFO(3, "/console/collect/configInfo", "监控配置信息")

    ;
    byte type;
    String desc;
    String url;

    public String getUrl() {
        return url;
    }

    public byte getType() {
        return type;
    }

    API(int type, String url, String desc) {
        this.type = (byte) type;
        this.url = url;
        this.desc = desc;
    }
}
