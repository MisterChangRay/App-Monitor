package com.github.misterchangray.appmonitor.appmonitorclient.service;

import com.github.misterchangra.appmonitor.base.command.cmd.FindInProcessCMD;
import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;
import com.github.misterchangra.appmonitor.base.dto.message.MonitorProcessInfo;
import com.github.misterchangray.appmonitor.appmonitorclient.service.config.Configuration;
import com.github.misterchangray.appmonitor.appmonitorclient.service.events.WarningMsgEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

@Service
public class ProcessProtectService {
    static Logger logger = LoggerFactory.getLogger(ProcessProtectService.class.getName());
    @Autowired
    private Configuration configuration;
    @Autowired
    private ApplicationContext applicationContext;
    private long lastRunTime = 0;
    private long secOf30 = 30 * 1000;
    public void start() {
        if(System.currentTimeMillis() - lastRunTime < secOf30) {
            logger.debug("执行周期太短, 忽略本次");
            return;
        }
        lastRunTime = System.currentTimeMillis();

        if(configuration.getProcessInfos() == null || configuration.getProcessInfos().size() == 0) {
            return;
        }

        for (MonitorProcessInfo processInfo : configuration.getProcessInfos()) {
            if(processInfo.getAutoRestart() == 0) {
                // 未启用自动启动
                continue;
            }


            if((processInfo.getCommType() & 0x1) == 0) {
                // 通过 SSH 执行命令方式进行进程保护， 直接忽略
                continue;
            }


            boolean running = true;
            switch (processInfo.getScanType()) {
                //  1. 通过端口监听
                //  2. 通过文件名来检测
                //  3. 通过自定义命令检测
                case 1:
                    running = portHasOpen(processInfo.getPort());
                    break;
                case 2:
                    running = checkProcessingByFileName(processInfo);
                    break;
                case 3:
                    running = checkProcessingByCustomScript(processInfo);
                    break;
            }

            if(running) {
                continue;
            }

            logger.info("检测到进程已结束, 启动进程 fullPath: {}", processInfo.getFullFilePath());
            startProcess(processInfo);

        }
    }


    /**
     * 启动进程
     * @param processInfo
     */
    private void startProcess(MonitorProcessInfo processInfo) {
        try {
            Runtime.getRuntime().exec(processInfo.getStartCmd(), null, new File(FullFilePathUtil.getProcessBaseDir(processInfo.getProcessBaseDir())));
        } catch (Exception e) {
            applicationContext.publishEvent(new WarningMsgEvent("执行启动脚本错误 -> " + e.toString()));
        }
    }

    /**
     * 通过自定义脚本确定进程运行状态
     * @param processInfo
     * @return
     */
    private boolean checkProcessingByCustomScript(MonitorProcessInfo processInfo) {
        try {
            Process proc = Runtime.getRuntime().exec(processInfo.getStartCmd(), null, new File(FullFilePathUtil.getProcessBaseDir(processInfo.getProcessBaseDir())));
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            String res = stdInput.readLine().trim();
            if(res.equalsIgnoreCase("TRUE") || res.equals("1")) {
                return true;
            }
        } catch (Exception e) {
            applicationContext.publishEvent(new WarningMsgEvent("执行脚本错误 -> " + e.toString()));
        }
        return false;
    }

    /**
     * 通过文件命名确认进程运行状态
     * @param processInfo
     * @return
     */
    private boolean checkProcessingByFileName(MonitorProcessInfo processInfo) {
        List<FindInProcessCMDResult> result = new FindInProcessCMD().execCmd(processInfo.getProcessFileName()).getResult();
        if(Objects.isNull(result)) {
            return false;
        }
        if(result.size() > 0) {
            return true;
        }
        return false;
    }


    /**
     * 通过端口来确认进程运行状态
     * @param port
     * @return
     */
    private boolean portHasOpen(String port) {
        if(port == null) {
            port = "";
        }
        port = port.trim();

        String[] ports = {port};
        if(port.contains(",")) {
            ports = port.split(",");
        }

        for (String p : ports) {
            try {
                Socket socket = new Socket("127.0.0.1", Integer.valueOf(p));
                socket.close();
                // 端口被使用则会抛出异常
                return true;
            } catch (IOException ignored) {
                return false;
            }
        }
        return false;
    }


}
