package com.github.changray.appmonitor.appmonitorserver.service.ssh;

import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHConnectInfo;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHExecuteInfo;
import com.github.changray.appmonitor.appmonitorserver.service.ssh.dto.SSHSessionContext;
import com.github.misterchangra.appmonitor.base.command.CommandExecutor;
import com.github.misterchangra.appmonitor.base.command.BaseCMDResult;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class SSHClient extends CommandExecutor {
    static Logger logger = LoggerFactory.getLogger(SSHClient.class.getName());

    /**
     * 0 初始化
     * 1 成功连接
     * 2 连接失败
     *
     */
    private CLIENT_STATUS status = CLIENT_STATUS.INIT;
    private ServerInfo serverInfo;


    public enum CLIENT_STATUS {
        INIT, CONNECT_SUCCESS, CONNECT_FAILED;
    }

    //创建一个ssh通讯核心类
    JSch jSch = new JSch();
    SSHSessionContext sessionContext;
    ExecutorService es1 = Executors.newSingleThreadExecutor();

    public CLIENT_STATUS getStatus() {
        return status;
    }

    public SSHSessionContext getSessionContext() {
        return sessionContext;
    }

    @Override
    public BaseCMDResult execute(CommandExecutor.SYSTEM system, String cmd) {
        if(system == SYSTEM.WINDOWS) {
            return new BaseCMDResult(null, false, "不支持windows");
        }

        SSHExecuteInfo sshExecuteInfo = new SSHExecuteInfo();
        sshExecuteInfo.setCommand(cmd);
        SSHExecuteInfo execute = this.execute(sshExecuteInfo);

        return new BaseCMDResult(new StringBuilder(execute.getResult()), execute.isSuccess(), execute.getDesc());
    }

    @Override
    public SYSTEM getSystem() {
        return SYSTEM.LINUX;
    }


    public SSHExecuteInfo execute(SSHExecuteInfo sshExecuteInfo) {
        Future<SSHExecuteInfo> exec = es1.submit(new Callable<SSHExecuteInfo>() {
            @Override
            public SSHExecuteInfo call() throws Exception {
                if(null == sessionContext) {
                    SSHConnectInfo test = test(sshExecuteInfo);
                    if (!test.isSuccess()) {
                        sshExecuteInfo.setSuccess(false);
                        sshExecuteInfo.setDesc(test.getDesc());
                        return sshExecuteInfo;
                    }
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


    public SSHConnectInfo test(ServerInfo serverInfo) {
        SSHConnectInfo sshConnectInfo = new SSHConnectInfo();
        BeanUtils.copyProperties(serverInfo, sshConnectInfo);
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            Session sessiont = jSch.getSession(serverInfo.getUsername(), serverInfo.getIp(), Integer.valueOf(serverInfo.getPort()));
            sessiont.setPassword(serverInfo.getPassword());
            sessiont.setTimeout(4000);
            sessiont.setConfig(config);
            sessiont.connect();
            sshConnectInfo.setSuccess(true);

            SSHSessionContext sshSessionContext = new SSHSessionContext();
            BeanUtils.copyProperties(serverInfo, sshSessionContext);
            sshSessionContext.setSession(sessiont);
            sessionContext = sshSessionContext;
            this.status = CLIENT_STATUS.CONNECT_SUCCESS;
        } catch (JSchException e) {
            this.status = CLIENT_STATUS.CONNECT_FAILED;
            sshConnectInfo.setSuccess(false);
            sshConnectInfo.setDesc(e.getMessage());
        }
        return sshConnectInfo;
    }


    public SSHClient() {
    }

    public SSHClient(ServerInfo sshConfig) {
        test(sshConfig);
    }


}
