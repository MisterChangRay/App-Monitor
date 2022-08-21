package com.github.changray.appmonitor.appmonitorserver.service.tasks;

import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.changray.appmonitor.appmonitorserver.service.CheckerTask;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.SSHClientService;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHExecuteInfo;
import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;
import com.github.misterchangra.appmonitor.base.util.TextUtil;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomCheckerTask extends CheckerTask {


    public CustomCheckerTask(AppInfo appInfo, ServerInfo serverInfo, SSHClientService sshClientService) {
        super(appInfo, serverInfo, sshClientService);
    }

    @Override
    public FindInProcessCMDResult check() {

        SSHExecuteInfo sshExecuteInfo = new SSHExecuteInfo();
        sshExecuteInfo.setCommand(String.format("cd %s && %s", this.appInfo.getDeployPath(), this.appInfo.getTestCmd()));
        SSHExecuteInfo execute = this.sshClientService.execute(sshExecuteInfo);

        String result = execute.getResult();
        if(Objects.nonNull(result) && result.length() > 3) {
            result = result.toUpperCase();
        } else {
            return null;
        }

        if(this.appInfo.getSystemType() == 0) {
            // systemType 0=linux 1=windows
            Matcher matcher = TextUtil.match0(result, Pattern.compile("\\s*(TRUE|FALSE)((?=:).+?)?((?=:).+?)?\\s*$"));
            if(matcher.find()) {
                FindInProcessCMDResult result1 = new FindInProcessCMDResult();
                result1.setSuccess(1);
                String res = null, pid = null ;

                if(matcher.groupCount() > 2) {
                    pid = matcher.group(2).substring(1);
                }

                if(matcher.groupCount() > 1) {
                    res = matcher.group(1);
                }

                if(res.contains("TRUE")) {
                    return new FindInProcessCMDResult(null, pid, null);
                } else {
                    return result1;
                }
            }
        }

        return null;
    }
}
