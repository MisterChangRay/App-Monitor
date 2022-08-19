package com.github.changray.appmonitor.appmonitorserver.service.ssh;

import com.github.changray.appmonitor.appmonitorserver.listeners.ProcessProtectListener;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHConfig;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHConnectInfo;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHExecuteInfo;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHSessionContext;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;

public class SSHClientService {
    static Logger logger = LoggerFactory.getLogger(SSHClientService.class.getName());

    SSHConfig sshConfig;
    //创建一个ssh通讯核心类
    JSch jSch = new JSch();
    SSHSessionContext sessionContext;
    ExecutorService es1 = Executors.newSingleThreadExecutor();


    public SSHExecuteInfo execute(SSHExecuteInfo sshExecuteInfo) {
        Future<SSHExecuteInfo> exec = es1.submit(new Callable<SSHExecuteInfo>() {
            @Override
            public SSHExecuteInfo call() throws Exception {
                SSHConnectInfo test = test(sshExecuteInfo);
                if (!test.isSuccess()) {
                    sshExecuteInfo.setSuccess(false);
                    sshExecuteInfo.setDesc(test.getDesc());
                    return sshExecuteInfo;
                }

                Session session = sessionContext.getSession();
                if (!session.isConnected()) {
                    try {
                        session.connect();
                    } catch (JSchException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
                    channelExec.setCommand(sshExecuteInfo.getCommand());
                    channelExec.connect();
                    InputStream inputStream = channelExec.getInputStream();

                    Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
                    String result = scanner.hasNext() ? scanner.next() : "";

                    sshExecuteInfo.setSuccess(true);
                    sshExecuteInfo.setResult(result);

                } catch (JSchException | IOException e) {
                    sshExecuteInfo.setSuccess(false);
                    sshExecuteInfo.setDesc(e.toString());
                    e.printStackTrace();
                }
                return sshExecuteInfo;
            }
        });
        try {
            return exec.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info("exception has occur: " ,e );
        }
        return null;
    }

    public List<SSHExecuteInfo> execute(List<SSHExecuteInfo> sshExecuteInfos) {
        for (SSHExecuteInfo sshExecuteInfo : sshExecuteInfos) {
            this.execute(sshExecuteInfo);
        }
        return sshExecuteInfos;
    }

    public SSHConnectInfo test(SSHConfig sshConfig) {
        SSHConnectInfo sshConnectInfo = new SSHConnectInfo();
        BeanUtils.copyProperties(sshConfig, sshConnectInfo);
        try {
            Session sessiont = jSch.getSession(sshConfig.getUsername(), sshConfig.getHost(), sshConfig.getPort());
            sessiont.setPassword(sshConfig.getPassword());
            sessiont.connect();
            sshConnectInfo.setSuccess(true);

            SSHSessionContext sshSessionContext = new SSHSessionContext();
            BeanUtils.copyProperties(sshConfig, sshSessionContext);
            sshSessionContext.setSession(sessiont);
            sessionContext = sshSessionContext;
        } catch (JSchException e) {
            sshConnectInfo.setSuccess(false);
            sshConnectInfo.setDesc(e.toString());
        }
        return sshConnectInfo;
    }


    public SSHClientService(SSHConfig sshConfig) {
        this.sshConfig = sshConfig;
    }


}
