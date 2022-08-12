package com.github.misterchangray.appmonitor.appmonitorclient.service.events;

import org.springframework.context.ApplicationEvent;

public class HeartbeatEvent extends ApplicationEvent {
    public HeartbeatEvent(Object source) {
        super(source);
    }
}
