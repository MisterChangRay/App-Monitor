package com.github.changray.appmonitor.appmonitorserver.listeners;

import com.github.changray.appmonitor.appmonitorserver.dao.AppInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.ServerInfoDao;
import com.github.changray.appmonitor.appmonitorserver.events.CollectRemoteSystemInfoEvent;
import com.github.changray.appmonitor.appmonitorserver.service.ClientManagerServers;
import com.github.changray.appmonitor.appmonitorserver.service.ConfigurationService;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.SSHClient;
import com.github.misterchangra.appmonitor.base.command.cmd.SystemInfoCMD;
import com.github.misterchangra.appmonitor.base.command.result.SystemInfoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 进程保护服务
 *
 * 这里主要是使用远程SSH 方式进行进程保护
 */
@Service
public class CollectRemoteSystemInfoListener {
    static Logger logger = LoggerFactory.getLogger(CollectRemoteSystemInfoListener.class.getName());

    @Autowired
    private AppInfoDao appInfoDao;
    @Autowired
    private ServerInfoDao serverInfoDao;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private ClientManagerServers clientManagerServers;



    @EventListener(value = {CollectRemoteSystemInfoEvent.class})
    public void start() {
        List<SSHClient> allSSHClients = clientManagerServers.getAllSSHClients();

        for (SSHClient client : allSSHClients) {
            if(client.getStatus() == SSHClient.CLIENT_STATUS.CONNECT_SUCCESS) {
                fetchRemoteSystemInfo(client);
            }
        }


    }

    private void fetchRemoteSystemInfo(SSHClient client) {
        SystemInfoCMD systemInfoCMD = new SystemInfoCMD(client);
        systemInfoCMD.execCmd( new String[0]);

        SystemInfoResult result = systemInfoCMD.getResult();


    }


}
