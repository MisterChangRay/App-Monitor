package com.github.changray.appmonitor.appmonitorserver.service.tasks;

import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.changray.appmonitor.appmonitorserver.service.CheckerTask;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.SSHClient;
import com.github.misterchangra.appmonitor.base.command.cmd.FindInProcessCMD;
import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;

import java.util.List;

public class FileNameCheckerTask extends CheckerTask {


    public FileNameCheckerTask(AppInfo appInfo, ServerInfo serverInfo, SSHClient sshClient) {
        super(appInfo, serverInfo, sshClient);
    }

    @Override
    public FindInProcessCMDResult.ProcessResult check() {
        FindInProcessCMD cmd = new FindInProcessCMD(this.sshClient);
        cmd.execCmd(appInfo.getDeployFile());

        FindInProcessCMDResult result1 = cmd.getResult();
        if(!result1.isSuccess()) {
            return null;
        }

        List<FindInProcessCMDResult.ProcessResult> result = result1.getProcessResultList();
        if(result.size() > 0) {
            return result.get(0);
        }

        return null;
    }
}
