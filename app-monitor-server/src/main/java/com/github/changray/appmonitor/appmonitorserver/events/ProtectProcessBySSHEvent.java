package com.github.changray.appmonitor.appmonitorserver.events;

import org.springframework.context.ApplicationEvent;

public class ProtectProcessBySSHEvent extends ApplicationEvent {
    public ProtectProcessBySSHEvent(Object source) {
        super(source);
    }
}
