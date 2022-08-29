package com.github.changray.appmonitor.appmonitorserver.service;

import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.SSHClient;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHExecuteInfo;
import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;

public abstract class CheckerTask {
    protected AppInfo appInfo;
    protected ServerInfo serverInfo;
    protected SSHClient sshClient;

    public CheckerTask(AppInfo appInfo, ServerInfo serverInfo, SSHClient sshClient) {
        this.appInfo = appInfo;
        this.serverInfo = serverInfo;
        this.sshClient = sshClient;
    }

    public abstract FindInProcessCMDResult.ProcessResult check();

    public void start() {
        sshClient.execute(SSHExecuteInfo.build(String.format("cd %s && %s", appInfo.getDeployPath(), appInfo.getStartCmd())));
    }
}
