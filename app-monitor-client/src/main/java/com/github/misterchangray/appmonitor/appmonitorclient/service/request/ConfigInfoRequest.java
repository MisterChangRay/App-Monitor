package com.github.misterchangray.appmonitor.appmonitorclient.service.request;

import com.github.misterchangra.appmonitor.base.dto.BaseRequest;

public class ConfigInfoRequest extends BaseRequest {
    public ConfigInfoRequest(String ip, Integer port) {
        super(ip, port);
    }
}
