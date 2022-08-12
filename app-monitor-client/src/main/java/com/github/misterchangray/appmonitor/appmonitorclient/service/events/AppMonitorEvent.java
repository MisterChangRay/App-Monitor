package com.github.misterchangray.appmonitor.appmonitorclient.service.events;

import org.springframework.context.ApplicationEvent;

public class AppMonitorEvent extends ApplicationEvent {
    public AppMonitorEvent(Object source) {
        super(source);
    }
}
