package com.github.misterchangra.appmonitor.base.command.cmd;

import com.github.misterchangra.appmonitor.base.command.BaseCommand;
import com.github.misterchangra.appmonitor.base.command.CommandExecutor;
import com.github.misterchangra.appmonitor.base.command.result.SystemInfoResult;
import com.github.misterchangra.appmonitor.base.util.TextUtil;

import java.util.regex.Pattern;

public class SystemInfoCMD extends BaseCommand<SystemInfoResult> {
    public SystemInfoCMD(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @Override
    public SystemInfoResult getResult() {

        if(this.executeResult == null) {
            return null;
        }
        if(!this.executeResult.isSuccess()) {
            return null;
        }

        StringBuilder result = this.executeResult.getData();

        SystemInfoResult res = new SystemInfoResult();
        if(this.getSystem() == CommandExecutor.SYSTEM.LINUX) {
            String[] lines = result.toString().split("\n");

            for (int i = 0; i < lines.length; i++) {
                this.readLine(lines[i], i, res);
            }
        }

        return res;
    }



    static Pattern line1 =  Pattern.compile(".*load average: (.+?), (.+?),.*");
    static Pattern line2 =  Pattern.compile(".*Threads: (.+?) total,.*");
    static Pattern line3 =  Pattern.compile(".*%Cpu\\(s\\):  (.+?) us,  (.+?) sy.*");
    static Pattern line4 =  Pattern.compile(".*MiB Mem :   (.+?) total,    (.+?) free,   (.+?) used.*");


    private void readLine(String line, int lineNumber, SystemInfoResult res) {
        if(lineNumber  == 0) {
           res.setAvgload(TextUtil.match(line, line1, 2));
        }
        if(lineNumber  == 1) {
            res.setThreads(TextUtil.match(line, line2));
        }
        if(lineNumber  == 2) {
            res.setCpuUserUsage(TextUtil.match(line, line3, 1));
            res.setCpuSysUsage(TextUtil.match(line, line3, 2));
        }
        if(lineNumber  == 3) {
            res.setTotalMem(TextUtil.match(line, line4, 1));
            res.setTotalMemUse(TextUtil.match(line, line4, 3));
        }

    }

    @Override
    public String getCommand(CommandExecutor.SYSTEM system) {
        switch (system) {
            case LINUX:
                return "top -H -n 1 |  head -5";
            case WINDOWS:
                return "";
        }
        return "";
    }
}
