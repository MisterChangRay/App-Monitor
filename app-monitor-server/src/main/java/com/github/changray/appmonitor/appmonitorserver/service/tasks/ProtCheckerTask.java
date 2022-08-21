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
import java.util.Objects;

public class ProtCheckerTask extends CheckerTask {


    public ProtCheckerTask(AppInfo appInfo, ServerInfo serverInfo, SSHClientService sshClientService) {
        super(appInfo, serverInfo, sshClientService);
    }

    @Override
    public FindInProcessCMDResult check() {

        FindInProcessByPortCMD cmd = new FindInProcessByPortCMD();

        SSHExecuteInfo sshExecuteInfo = new SSHExecuteInfo();

        String port = this.appInfo.getPort();
        if(this.appInfo.getPort().contains(",")) {
            port = this.appInfo.getPort().split(",")[0];
        }
        sshExecuteInfo.setCommand(String.format(cmd.getCommand(appInfo.getSystemType()), port));
        SSHExecuteInfo execute = sshClientService.execute(sshExecuteInfo);
        if(execute.isSuccess()) {
            cmd.setResult(new StringBuilder(sshExecuteInfo.getResult()));
        }

        List<FindInProcessCMDResult> result = cmd.getResult(appInfo.getSystemType());
        if(Objects.nonNull(result) && result.size() > 0) {
            return result.get(0);
        }

        return null;
    }
}
