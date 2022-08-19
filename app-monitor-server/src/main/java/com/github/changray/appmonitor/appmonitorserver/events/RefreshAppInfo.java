package com.github.changray.appmonitor.appmonitorserver.events;

import org.springframework.context.ApplicationEvent;

import java.util.Objects;

public class RefreshAppInfo extends ApplicationEvent {
    private String ip;
    public RefreshAppInfo(String ip, Object source) {
        super(source);
        this.ip = ip;
    }
}
