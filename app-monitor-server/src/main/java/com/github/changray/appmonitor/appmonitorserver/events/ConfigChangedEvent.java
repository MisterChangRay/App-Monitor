package com.github.changray.appmonitor.appmonitorserver.events;

import org.springframework.context.ApplicationEvent;

public class ConfigChangedEvent extends ApplicationEvent {
    public ConfigChangedEvent(Object source) {
        super(source);
    }
}
