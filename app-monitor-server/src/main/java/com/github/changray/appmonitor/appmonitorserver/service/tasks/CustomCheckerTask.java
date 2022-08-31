package com.github.changray.appmonitor.appmonitorserver.service.tasks;

import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.changray.appmonitor.appmonitorserver.listeners.ProcessProtectListener;
import com.github.changray.appmonitor.appmonitorserver.service.CheckerTask;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.SSHClient;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHExecuteInfo;
import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;
import com.github.misterchangra.appmonitor.base.util.TextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomCheckerTask extends CheckerTask {
    static Logger logger = LoggerFactory.getLogger(CustomCheckerTask.class.getName());

    public CustomCheckerTask(AppInfo appInfo, ServerInfo serverInfo, SSHClient sshClient) {
        super(appInfo, serverInfo, sshClient);
    }

    @Override
    public FindInProcessCMDResult.ProcessResult check() {

        SSHExecuteInfo sshExecuteInfo = new SSHExecuteInfo();
        sshExecuteInfo.setCommand(String.format("cd %s && %s", this.appInfo.getDeployPath(), this.appInfo.getTestCmd()));
        SSHExecuteInfo execute = this.sshClient.execute(sshExecuteInfo);

        String result = execute.getResult();
        logger.info("[远程指令执行] host:{}, 返回: {}", this.sshClient.getSessionContext().getIp(), result);
        if(Objects.nonNull(result) && result.length() > 3) {
            result = result.toUpperCase();
        } else {
            return null;
        }


        if(this.serverInfo.getSystemType() == 0) {
            // systemType 0=linux 1=windows
            Matcher matcher = TextUtil.match0(result, Pattern.compile("\\s*(TRUE|FALSE)((?=:).+?)?((?=:).+?)?\\s*$"));
            if(matcher.find()) {
                FindInProcessCMDResult result1 = new FindInProcessCMDResult(new ArrayList<>(), true);
                result1.setSuccess(true);
                String res = null, pid = null ;

                if(matcher.groupCount() > 2) {
                    pid = matcher.group(2).substring(1);
                }

                if(matcher.groupCount() > 1) {
                    res = matcher.group(1);
                }

                if(res.contains("TRUE")) {
                    return new FindInProcessCMDResult.ProcessResult(null, pid, null);
                } else {
                    return null;
                }
            }
        }

        return null;
    }
}
