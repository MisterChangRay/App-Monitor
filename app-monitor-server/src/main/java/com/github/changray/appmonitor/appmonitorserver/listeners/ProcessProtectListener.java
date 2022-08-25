package com.github.changray.appmonitor.appmonitorserver.listeners;

import com.github.changray.appmonitor.appmonitorserver.dao.AppInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.ServerInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.changray.appmonitor.appmonitorserver.events.ConfigChangedEvent;
import com.github.changray.appmonitor.appmonitorserver.events.ProtectProcessBySSHEvent;
import com.github.changray.appmonitor.appmonitorserver.service.CheckerTask;
import com.github.changray.appmonitor.appmonitorserver.service.ClientServers;
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
public class ProcessProtectListener {
    static Logger logger = LoggerFactory.getLogger(ProcessProtectListener.class.getName());


    @Autowired
    private ClientServers clientServers;
    @Autowired
    private AppInfoDao appInfoDao;

    @Autowired
    private ConfigurationService configurationService;
    List<AppInfo> allRemoteTask = null;

    @EventListener(value = {ConfigChangedEvent.class})
    public void configChanges() {
        this.allRemoteTask = null;
    }

    @EventListener(value = {ProtectProcessBySSHEvent.class})
    public void start() {
        if(Objects.isNull(allRemoteTask) || allRemoteTask.size() == 0) {
            allRemoteTask = configurationService.getAllRemoteTask();
        }

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
                    checkerTask = new ProtCheckerTask(processInfo, clientServers.getClientInfoByIp(processInfo.getServerIp()),
                            clientServers.getSSHClientByIp(processInfo.getServerIp()));
                    break;
                case 2:
                    checkerTask = new FileNameCheckerTask(processInfo, clientServers.getClientInfoByIp(processInfo.getServerIp()),
                            clientServers.getSSHClientByIp(processInfo.getServerIp()));
                    break;
                case 3:
                    checkerTask = new CustomCheckerTask(processInfo, clientServers.getClientInfoByIp(processInfo.getServerIp()),
                            clientServers.getSSHClientByIp(processInfo.getServerIp()));
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
