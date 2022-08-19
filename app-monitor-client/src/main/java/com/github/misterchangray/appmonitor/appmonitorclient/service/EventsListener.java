package com.github.misterchangray.appmonitor.appmonitorclient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.misterchangra.appmonitor.base.consts.API;
import com.github.misterchangra.appmonitor.base.dto.BaseResult;
import com.github.misterchangra.appmonitor.base.dto.message.ClientInfo;
import com.github.misterchangra.appmonitor.base.dto.message.MonitorProcessInfo;
import com.github.misterchangray.appmonitor.appmonitorclient.service.config.Configuration;
import com.github.misterchangray.appmonitor.appmonitorclient.service.events.AppMonitorEvent;
import com.github.misterchangray.appmonitor.appmonitorclient.service.events.HeartbeatEvent;
import com.github.misterchangray.appmonitor.appmonitorclient.service.events.RefreshConfigEvent;
import com.github.misterchangray.appmonitor.appmonitorclient.service.events.WarningMsgEvent;
import com.github.misterchangray.appmonitor.appmonitorclient.service.request.ConfigInfoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class EventsListener {
    static Logger logger = LoggerFactory.getLogger(EventsListener.class.getName());
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private Configuration configuration;
    @Autowired
    private ProcessProtectService processProtectService;

    @Autowired
    private HeartbeatService heartbeatService;


    /**
     *
     * 保存警告消息, 上报消息时将会附带上传
     *
     */
    @EventListener(value = {WarningMsgEvent.class})
    public void warningMsgEvent(WarningMsgEvent s) {
        if(configuration.getWarningMsgQueue().size() > 0xff) {
            // 超过 255 条就不上报了
            return;
        }
        logger.debug("增加警告消息 {}", s.getMsg());
        configuration.getWarningMsgQueue().add(s.getMsg());
    }

    /**
     *
     * 同步监控任务
     * 定时触发
     */
    @EventListener(value = {RefreshConfigEvent.class, ContextStartedEvent.class})
    public void refreshConfig() {
        if(!configuration.hasServerConfig()) {
            // 服务器配置无效, 直接返回
            return;
        }

        ClientInfo clientInfo = new ClientInfo(configuration.getClientIp(), configuration.getClientName(), configuration.getConfigSign());
        String param = null;
        try {
            param = objectMapper.writeValueAsString(clientInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String s = new ConfigInfoRequest(configuration.getServerIp(), configuration.getServerPort()).doRequest(API.CONFIG_INFO, param);
        if(!StringUtils.hasLength(s)) {
            return;
        }
        BaseResult<List<MonitorProcessInfo>> res = null;
        try {
            res = objectMapper.readValue(s, new TypeReference<BaseResult<List<MonitorProcessInfo>>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(Objects.isNull(res)) {
            return;
        }

        if(res.getCode() == 0 && Objects.nonNull(res.getData()) && res.getData().size() > 0) {
            logger.info("配置文件更新 {}", s);
            configuration.setProcessInfos(res.getData());
            configuration.setConfigSign(Long.valueOf(res.getSign()));
        }
    }

    /**
     * 应用监控事件
     * 进程保护
     */
    @EventListener(value = AppMonitorEvent.class)
    public void appMonitor() {
        logger.debug("开始执行应用保护");
        processProtectService.start();
    }


    /**
     * 定时触发 + 事件触发
     *
     * 心跳上报
     */
    @EventListener(value = HeartbeatEvent.class)
    public void heatbeat() {
        if(!configuration.hasServerConfig()) {
            // 服务器配置无效, 直接返回
            return;
        }

        heartbeatService.doPostHeatbeat();


    }
}
