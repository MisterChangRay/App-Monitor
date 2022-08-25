package com.github.changray.appmonitor.appmonitorserver.service;

import com.github.changray.appmonitor.appmonitorserver.events.ConfigChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 统一封装一些事件的触发
 */
@Service
public class EventsService {
    @Autowired
    private ApplicationContext applicationContext;


    public void publishConfigChangeEvent() {
        applicationContext.publishEvent(new ConfigChangedEvent(this));
    }
}
