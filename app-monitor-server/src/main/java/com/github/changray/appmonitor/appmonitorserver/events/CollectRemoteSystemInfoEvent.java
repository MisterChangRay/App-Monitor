package com.github.changray.appmonitor.appmonitorserver.events;

import com.github.changray.appmonitor.appmonitorserver.service.ScheduleTriggerService;
import org.springframework.context.ApplicationEvent;

public class CollectRemoteSystemInfoEvent extends ApplicationEvent {
    public CollectRemoteSystemInfoEvent(ScheduleTriggerService scheduleTriggerService) {
        super(scheduleTriggerService);
    }
}
