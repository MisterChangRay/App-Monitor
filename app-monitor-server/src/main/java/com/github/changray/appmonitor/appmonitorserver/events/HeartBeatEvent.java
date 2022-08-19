package com.github.changray.appmonitor.appmonitorserver.events;

import org.springframework.context.ApplicationEvent;

public class HeartBeatEvent extends ApplicationEvent {
    public HeartBeatEvent(Object source) {
        super(source);
    }
}
