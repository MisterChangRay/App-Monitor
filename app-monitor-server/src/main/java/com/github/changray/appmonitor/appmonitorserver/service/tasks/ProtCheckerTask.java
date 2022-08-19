package com.github.changray.appmonitor.appmonitorserver.service.tasks;

import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.changray.appmonitor.appmonitorserver.service.CheckerTask;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.SSHClientService;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHExecuteInfo;
import com.github.misterchangra.appmonitor.base.command.FindInProcessByPortCMD;
import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;
import com.sun.xml.bind.v2.schemagen.xmlschema.Appinfo;

import java.util.List;

public class ProtCheckerTask extends CheckerTask {


    public ProtCheckerTask(AppInfo appInfo, ServerInfo serverInfo, SSHClientService sshClientService) {
        super(appInfo, serverInfo, sshClientService);
    }

    @Override
    public boolean check() {

        FindInProcessByPortCMD cmd = new FindInProcessByPortCMD();

        SSHExecuteInfo sshExecuteInfo = new SSHExecuteInfo();
        sshExecuteInfo.setCommand(cmd.getCommand());
        SSHExecuteInfo execute = sshClientService.execute(sshExecuteInfo);
        if(execute.isSuccess()) {
            cmd.setResult(new StringBuilder(sshExecuteInfo.getResult()));
        }

        List<FindInProcessCMDResult> result = cmd.getResult();
        if(result.size() > 0) {
            return true;
        }

        return false;
    }
}
