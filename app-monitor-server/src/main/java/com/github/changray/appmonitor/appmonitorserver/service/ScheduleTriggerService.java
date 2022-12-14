package com.github.changray.appmonitor.appmonitorserver.service;

import com.github.changray.appmonitor.appmonitorserver.events.CollectRemoteSystemInfoEvent;
import com.github.changray.appmonitor.appmonitorserver.events.HeartBeatEvent;
import com.github.changray.appmonitor.appmonitorserver.events.ProtectProcessBySSHEvent;
import com.github.changray.appmonitor.appmonitorserver.events.SystenInitEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 计划任务调度器
 *
 * 对于一些定时任务 进行事件触发
 */
@Service
public class ScheduleTriggerService {
    @Autowired
    ApplicationContext applicationContext;


    /**
     * 每5秒广播一次服务器信息
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void heatbeat() {
        applicationContext.publishEvent(new HeartBeatEvent(this));
    }


    /**
     * X系统初始化
     */
    @Scheduled(initialDelay = 0, fixedRate = Integer.MAX_VALUE)
    public void systemInitEvent() {
        applicationContext.publishEvent(new SystenInitEvent(this));
    }


    /**
     * 每 5 分钟执行进程保护
     */
    @Scheduled(initialDelay = 100, fixedRate = 5 * 60 * 1000)
    public void protectProcessBySSH() {
        applicationContext.publishEvent(new ProtectProcessBySSHEvent(this));
    }



    /**
     * 每 1 分钟执行系统信息手机
     */
    @Scheduled(initialDelay = 100, fixedRate = 60 * 1000)
    public void collectRemoteSystemInfo() {
        applicationContext.publishEvent(new CollectRemoteSystemInfoEvent(this));
    }


}
