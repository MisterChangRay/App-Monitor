package com.github.misterchangra.appmonitor.base.dto;

import com.github.misterchangra.appmonitor.base.consts.API;
import com.github.misterchangra.appmonitor.base.util.HTTPUtil;

public class BaseRequest<R> {
    public  String ip;
    public  int port;

    public BaseRequest(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String doRequest(API api, String param) {
        String path = api.getUrl();
        if(!path.startsWith("/")) {
            path = "/" + path;
        }
        String url = String.format("http://%s:%s%s", ip, port, path);
        String s = HTTPUtil.doPost(url, param);
        return s;
    }
}
