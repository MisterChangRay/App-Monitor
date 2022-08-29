package com.github.changray.appmonitor.appmonitorserver.service;

import com.github.changray.appmonitor.appmonitorserver.dao.ServerInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.changray.appmonitor.appmonitorserver.events.ConfigChangedEvent;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.SSHClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ClientManagerServers {
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
    private Map<String, SSHClient> sshClientServices = new HashMap<>();


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


            if(!StringUtils.hasLength(serverInfo.getUsername()) || !StringUtils.hasLength(serverInfo.getPassword())) {
                continue;
            }
            serverCache.put(serverInfo.getIp(), serverInfo);
        }
    }


    public List<SSHClient> getAllSSHClients() {
        if(Objects.isNull(serverCache) || serverCache.size() == 0 ) {
            refreshServerInfo();
        }

        List<SSHClient> res = new ArrayList<>();
        for (String s : sshClientServices.keySet()) {
            res.add(sshClientServices.get(s));
        }
        return res;
    }

    public SSHClient getSSHClientByIp(String ip) {
        SSHClient sshClient = null;
        if(!sshClientServices.containsKey(ip)) {
            ServerInfo serverInfo = this.getClientInfoByIp(ip);
            sshClient = new SSHClient(serverInfo);
            sshClientServices.put(serverInfo.getIp(), sshClient);
        } else {
            sshClientServices.get(ip);
        }
        return sshClient;
    }


    public ServerInfo getClientInfoByIp(String serverIp) {
        if(Objects.isNull(serverCache) || serverCache.size() == 0 ) {
            refreshServerInfo();
        }
        return serverCache.get(serverIp);
    }
}
