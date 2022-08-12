package com.github.changray.appmonitor.appmonitorserver.controller;

import com.github.changray.appmonitor.appmonitorserver.dao.AppInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.ServerInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.misterchangra.appmonitor.base.dto.BaseResult;
import com.github.misterchangra.appmonitor.base.dto.message.ClientInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/collect")
@RestController
public class DataCollectController {
    static Logger logger = LoggerFactory.getLogger(DataCollectController.class.getName());

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

        logger.info("收到客户端心跳请求: {}, 错误信息: {}", clientInfo.getIp(), clientInfo.getWarning().toString());
        return BaseResult.success();
    }


    /**
     * 同步配置信息
     * @param clientInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/configInfo")
    public BaseResult configInfo(@RequestBody ClientInfo clientInfo) {
        List<AppInfo> allByIp = appInfoDao.findAllByIp(clientInfo.getIp());
        long sign = sign(allByIp);
        if(clientInfo.getConfigSign() == sign) {
            return BaseResult.success();
        }


        logger.info("收到客户端配置同步请求: {}, sign: {}", clientInfo.getIp(), clientInfo.getConfigSign());
        return BaseResult.success().setData(allByIp).setSign(sign + "");
    }

    private long sign(List<AppInfo> appInfos) {
        if(appInfos == null || appInfos.size() == 0) {
            return 0;
        }
        long res = 0;
        for (AppInfo appInfo : appInfos) {
            res += appInfo.getUpdateTime().getTime();
        }
        return res;
    }


    @RequestMapping("/appinfo")
    public BaseResult appinfo(@RequestParam(value = "ip") String ip) {

        return BaseResult.success();
    }
}
