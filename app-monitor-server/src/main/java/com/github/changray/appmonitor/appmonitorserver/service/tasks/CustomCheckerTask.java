package com.github.changray.appmonitor.appmonitorserver.service.tasks;

import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.changray.appmonitor.appmonitorserver.service.CheckerTask;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.SSHClientService;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHExecuteInfo;

public class CustomCheckerTask extends CheckerTask {


    public CustomCheckerTask(AppInfo appInfo, ServerInfo serverInfo, SSHClientService sshClientService) {
        super(appInfo, serverInfo, sshClientService);
    }

    @Override
    public boolean check() {

        SSHExecuteInfo sshExecuteInfo = new SSHExecuteInfo();
        sshExecuteInfo.setCommand(this.appInfo.getTestCmd());
        SSHExecuteInfo execute = this.sshClientService.execute(sshExecuteInfo);

        String result = execute.getResult();
        if("TRUE".equalsIgnoreCase(result)) {
            return true;
        }

        return false;
    }
}
