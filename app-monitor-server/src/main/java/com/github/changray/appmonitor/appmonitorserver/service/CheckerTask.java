package com.github.changray.appmonitor.appmonitorserver.service;

import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.SSHClientService;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHExecuteInfo;
import com.github.misterchangra.appmonitor.base.util.FullFilePathUtil;

public abstract class CheckerTask {
    protected AppInfo appInfo;
    protected ServerInfo serverInfo;
    protected SSHClientService sshClientService;

    public CheckerTask(AppInfo appInfo, ServerInfo serverInfo, SSHClientService sshClientService) {
        this.appInfo = appInfo;
        this.serverInfo = serverInfo;
        this.sshClientService = sshClientService;
    }

    public abstract boolean check();

    public void start() {
        sshClientService.execute(SSHExecuteInfo.build("cd " + FullFilePathUtil.getProcessBaseDir(appInfo.getFullFilePath())));
        sshClientService.execute(SSHExecuteInfo.build(appInfo.getStartCmd()));
    }
}
