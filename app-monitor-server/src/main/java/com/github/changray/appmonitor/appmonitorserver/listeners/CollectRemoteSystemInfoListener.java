package com.github.changray.appmonitor.appmonitorserver.listeners;

import com.github.changray.appmonitor.appmonitorserver.dao.AppInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.ServerInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.changray.appmonitor.appmonitorserver.events.ProtectProcessBySSHEvent;
import com.github.changray.appmonitor.appmonitorserver.service.CheckerTask;
import com.github.changray.appmonitor.appmonitorserver.service.ConfigurationService;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.SSHClientService;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHConfig;
import com.github.changray.appmonitor.appmonitorserver.service.tasks.CustomCheckerTask;
import com.github.changray.appmonitor.appmonitorserver.service.tasks.FileNameCheckerTask;
import com.github.changray.appmonitor.appmonitorserver.service.tasks.ProtCheckerTask;
import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;


/**
 * 进程保护服务
 *
 * 这里主要是使用远程SSH 方式进行进程保护
 */
@Service
public class CollectRemoteSystemInfoListener {
    static Logger logger = LoggerFactory.getLogger(CollectRemoteSystemInfoListener.class.getName());
    // ip, serverInfo
    private Map<String, ServerInfo> serverCache = new HashMap<String, ServerInfo>();
    private Map<String, SSHClientService> sshClientServices = new HashMap<>();

    @Value("global.config.ssh.username:")
    private String globalUsername;
    @Value("global.config.ssh.password:")
    private String globalPassword;
    @Value("global.config.ssh.port:")
    private String globalPort;

    @Autowired
    private AppInfoDao appInfoDao;
    @Autowired
    private ServerInfoDao serverInfoDao;
    @Autowired
    private ConfigurationService configurationService;



    @PostConstruct()
    public void refreshAppInfo() {
        refreshServerInfo();
        List<AppInfo> all = appInfoDao.findAll();
        for (AppInfo appInfo : all) {
            if(appInfo.getAutoRestart() == 0) {
                // 未启用自动启动
                continue;
            }

            if(0 == (appInfo.getCommType() & 0x2)) {
                // 非指定通过通过 SSH 执行命令方式进行进程保护， 直接忽略
                continue;
            }

            initAppInfo(appInfo);
        }
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

    private void initAppInfo(AppInfo appInfo) {
        ServerInfo serverInfo = serverCache.get(appInfo.getServerIp());
        if(Objects.isNull(serverInfo)) {
            return;
        }

        SSHConfig sshConfig = new SSHConfig(serverInfo.getUsername(), serverInfo.getPassword(),serverInfo.getIp(), Integer.valueOf(serverInfo.getPort()));

        sshClientServices.put(sshConfig.getHost(), new SSHClientService(sshConfig));
    }


    @EventListener(value = {ProtectProcessBySSHEvent.class})
    public void start() {

        List<AppInfo> allRemoteTask = configurationService.getAllRemoteTask();

        for (AppInfo processInfo : allRemoteTask) {
            if(processInfo.getAutoRestart() == 0) {
                // 未启用自动启动
                continue;
            }


            if(0 == (processInfo.getCommType() & 0x2)) {
                // 第 2 bit == 1 则开启， 0 则为关闭
                // 通过 SSH 执行命令方式进行进程保护， 直接忽略
                continue;
            }


            CheckerTask checkerTask= null;
            switch (processInfo.getScanType()) {
                //  1. 通过端口监听
                //  2. 通过文件名来检测
                //  3. 通过自定义命令检测
                case 1:
                    checkerTask = new ProtCheckerTask(processInfo, serverCache.get(processInfo.getServerIp()), sshClientServices.get(processInfo.getServerIp()));
                    break;
                case 2:
                    checkerTask = new FileNameCheckerTask(processInfo, serverCache.get(processInfo.getServerIp()), sshClientServices.get(processInfo.getServerIp()));
                    break;
                case 3:
                    checkerTask = new CustomCheckerTask(processInfo, serverCache.get(processInfo.getServerIp()), sshClientServices.get(processInfo.getServerIp()));
                    break;
            }

            FindInProcessCMDResult check = checkerTask.check();

            refreshAppData(processInfo, check);

            if(Objects.nonNull(check)) {
                // 非空则表示进程存在
                continue;
            }
            logger.info("检测到进程已结束, 启动进程 path: {}, name: {}", processInfo.getDeployPath(), processInfo.getDeployFile());
            checkerTask.start();

        }
    }

    /**
     * 更新应用状态
     * @param processInfo
     * @param check
     */
    private void refreshAppData(AppInfo processInfo, FindInProcessCMDResult check) {

        Optional<AppInfo> byId = appInfoDao.findById(processInfo.getId());
        AppInfo appInfo = byId.get();
        if(check == null) {
            appInfo.setStatus(3);
        } else {
            appInfo.setStatus(1);
            appInfo.setProcessId(check.getPid());
            appInfo.setUpdateTime(new Date());
        }

        appInfoDao.saveAndFlush(appInfo);
    }

}
