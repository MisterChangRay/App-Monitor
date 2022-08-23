package com.github.changray.appmonitor.appmonitorserver.events;

import org.springframework.context.ApplicationEvent;

import java.util.Objects;

public class SystenInitEvent extends ApplicationEvent {
    public SystenInitEvent(Object source) {
        super(source);
    }
}
