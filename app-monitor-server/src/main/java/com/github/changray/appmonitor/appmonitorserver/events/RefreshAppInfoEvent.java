package com.github.changray.appmonitor.appmonitorserver.events;

import org.springframework.context.ApplicationEvent;

import java.util.Objects;

public class RefreshAppInfoEvent extends ApplicationEvent {
    private String ip;
    public RefreshAppInfoEvent(String ip, Object source) {
        super(source);
        this.ip = ip;
    }
}
