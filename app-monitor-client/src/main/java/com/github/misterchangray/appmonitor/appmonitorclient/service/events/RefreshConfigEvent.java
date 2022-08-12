package com.github.misterchangray.appmonitor.appmonitorclient.service.events;

import org.springframework.context.ApplicationEvent;


public class RefreshConfigEvent extends ApplicationEvent {
    public RefreshConfigEvent(Object source) {
        super(source);
    }
}
