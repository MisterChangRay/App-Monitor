package com.github.changray.appmonitor.appmonitorserver.controller;

import com.github.changray.appmonitor.appmonitorserver.dao.AppInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.ServerInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.misterchangra.appmonitor.base.dto.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/collect")
@RestController
public class DataCollectController {
    @Autowired
    private AppInfoDao appInfoDao;
    @Autowired
    private ServerInfoDao serverInfoDao;

    @RequestMapping("/clientinfo")
    public BaseResult clientinfo(@RequestBody ServerInfo serverInfo) {

        Optional<ServerInfo> byId = serverInfoDao.findById(serverInfo.getIp());
        if(byId.isEmpty()) {
            serverInfoDao.save(serverInfo);
        }
        return BaseResult.success();
    }

    @RequestMapping("/appinfo")
    public BaseResult appinfo(@RequestParam(value = "ip") String ip) {

        return BaseResult.success();
    }
}
