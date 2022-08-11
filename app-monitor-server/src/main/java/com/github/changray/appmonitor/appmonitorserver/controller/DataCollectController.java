package com.github.changray.appmonitor.appmonitorserver.controller;

import com.github.changray.appmonitor.appmonitorserver.dao.AppInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.ServerInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.misterchangra.appmonitor.base.dto.BaseResult;
import com.github.misterchangra.appmonitor.base.dto.message.ClientInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/collect")
@RestController
public class DataCollectController {
    @Autowired
    private AppInfoDao appInfoDao;
    @Autowired
    private ServerInfoDao serverInfoDao;

    /**
     * 心跳回调时, 这里将会主动发起
     * @param clientInfo
     * @return
     */
    @RequestMapping("/clientinfo")
    public BaseResult clientinfo(@RequestBody ClientInfo clientInfo) {
        List<ServerInfo> serverInfos = serverInfoDao.findByIp(clientInfo.getIp());
        if(Objects.nonNull(serverInfos) && serverInfos.size() > 0) {
            for (ServerInfo serverInfo : serverInfos) {
                if(serverInfo.getStatus() == 1) {
                    serverInfo.setStatus(2);
                }
                serverInfo.setReportTime(new Date());
                serverInfo.setUpdateTime(serverInfo.getReportTime());
                serverInfoDao.save(serverInfo);
            }
        } else {
            ServerInfo serverInfo = new ServerInfo();
            serverInfo.setIp(clientInfo.getIp());
            serverInfo.setReportTime(new Date());
            serverInfo.setStatus(2);
            serverInfo.setCreateTime(new Date());
            serverInfo.setRemark("自动上报");
            serverInfoDao.save(serverInfo);
        }
        return BaseResult.success();
    }

    @RequestMapping("/appinfo")
    public BaseResult appinfo(@RequestParam(value = "ip") String ip) {

        return BaseResult.success();
    }
}
