package com.github.misterchangray.appmonitor.appmonitorclient.service.events;

import org.springframework.context.ApplicationEvent;

public class WarningMsgEvent extends ApplicationEvent {
    private String msg;
    public WarningMsgEvent(String source) {
        super(source);
        this.msg = source;
    }

    public String getMsg() {
        return msg;
    }
}
