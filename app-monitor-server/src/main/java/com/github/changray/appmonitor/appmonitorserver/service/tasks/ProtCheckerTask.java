package com.github.changray.appmonitor.appmonitorserver.service.tasks;

import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.changray.appmonitor.appmonitorserver.service.CheckerTask;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.SSHClient;
import com.github.misterchangra.appmonitor.base.command.cmd.FindInProcessByPortCMD;
import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;

import java.util.Objects;

public class ProtCheckerTask extends CheckerTask {


    public ProtCheckerTask(AppInfo appInfo, ServerInfo serverInfo, SSHClient sshClient) {
        super(appInfo, serverInfo, sshClient);
    }

    @Override
    public FindInProcessCMDResult.ProcessResult check() {

        String port = this.appInfo.getPort();
        if(this.appInfo.getPort().contains(",")) {
            port = this.appInfo.getPort().split(",")[0];
        }

        FindInProcessByPortCMD cmd = new FindInProcessByPortCMD(this.sshClient);
        cmd.execCmd(port);

        FindInProcessCMDResult result1 = cmd.getResult();
        if(result1.isSuccess()) {
            if(Objects.nonNull(result1) && result1.getProcessResultList().size() > 0) {
                return result1.getProcessResultList().get(0);
            }

        }


        return null;
    }
}
