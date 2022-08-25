package com.github.changray.appmonitor.appmonitorserver.service;

import com.github.changray.appmonitor.appmonitorserver.dao.ServerInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.changray.appmonitor.appmonitorserver.events.ConfigChangedEvent;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.SSHClientService;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ClientServers {
    @Autowired
    private ServerInfoDao serverInfoDao;
    @Value("global.config.ssh.username:")
    private String globalUsername;
    @Value("global.config.ssh.password:")
    private String globalPassword;
    @Value("global.config.ssh.port:")
    private String globalPort;
    // ip, serverInfo
    private Map<String, ServerInfo> serverCache = new HashMap<String, ServerInfo>();
    private Map<String, SSHClientService> sshClientServices = new HashMap<>();


    @EventListener(value = {ConfigChangedEvent.class})
    public void configChanges() {
        this.serverCache = null;
        this.sshClientServices = null;
    }

    private void refreshServerInfo() {
        List<ServerInfo> all = serverInfoDao.findAll();
        if(Objects.isNull(all)) {
            return;
        }

        for (ServerInfo serverInfo : all) {
            if(!StringUtils.hasLength(serverInfo.getPassword())) {
                serverInfo.setPassword(globalPassword);
            }
            if(!StringUtils.hasLength(serverInfo.getUsername())) {
                serverInfo.setUsername(globalUsername);
            }
            if(!StringUtils.hasLength(serverInfo.getPort())) {
                serverInfo.setPort(globalPort);
            }

            serverCache.put(serverInfo.getIp(), serverInfo);
        }
    }

    public SSHClientService getSSHClientByIp(String ip) {
        ServerInfo serverInfo = this.getClientInfoByIp(ip);
        SSHConfig sshConfig = new SSHConfig(serverInfo.getUsername(), serverInfo.getPassword(),serverInfo.getIp(), Integer.valueOf(serverInfo.getPort()));
        SSHClientService sshClientService = new SSHClientService(sshConfig);
        sshClientServices.put(sshConfig.getHost(), sshClientService);
        return sshClientService;
    }


    public ServerInfo getClientInfoByIp(String serverIp) {
        if(Objects.isNull(serverCache) || serverCache.size() == 0 ) {
            refreshServerInfo();
        }
        return serverCache.get(serverIp);
    }
}
