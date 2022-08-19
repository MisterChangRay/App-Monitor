package com.github.changray.appmonitor.appmonitorserver.service;

import com.github.changray.appmonitor.appmonitorserver.dao.AppInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.ServerInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationService {
    @Autowired
    private AppInfoDao appInfoDao;
    @Autowired
    private ServerInfoDao serverInfoDao;



    public List<AppInfo> getAllRemoteTask() {
        List<AppInfo> taskInfos = appInfoDao.findAllRemoteTask();
        return taskInfos;
    }
}
