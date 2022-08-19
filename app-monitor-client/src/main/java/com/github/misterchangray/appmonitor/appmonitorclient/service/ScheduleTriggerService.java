package com.github.misterchangray.appmonitor.appmonitorclient.service;

import com.github.misterchangra.appmonitor.base.dto.message.ServerInfo;
import com.github.misterchangray.appmonitor.appmonitorclient.service.events.AppMonitorEvent;
import com.github.misterchangray.appmonitor.appmonitorclient.service.events.HeartbeatEvent;
import com.github.misterchangray.appmonitor.appmonitorclient.service.events.RefreshConfigEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.EventListener;

/**
 * 事件触发器
 *
 * 对于一些定时任务 进行事件触发
 */
@Service
public class ScheduleTriggerService {
    @Autowired
    EventsListener eventListener;
    @Autowired
    ApplicationContext applicationContext;

    /**
     * 每 1 分钟执行一次进程扫描
     *
     */
    @Scheduled(initialDelay=100, fixedRate = 60 * 1000)
    public void appMonitor() {
        applicationContext.publishEvent(new AppMonitorEvent(this));
    }


    /**
     * 每隔 15S 主动上报心跳
     *
     * 或者 收到服务器心跳广播信息后自动上报心跳
     *
     */
    @Scheduled(initialDelay=100, fixedRate = 30 * 1000)
    public void heartbeatSchedule() {
        applicationContext.publishEvent(new HeartbeatEvent(this));
    }

    /**
     *每隔 30S 主动刷新配置信息
     */
    @Scheduled(initialDelay=100, fixedRate = 30 * 1000)
    public void refreshConfig() {
        applicationContext.publishEvent(new RefreshConfigEvent(this));
    }
}
