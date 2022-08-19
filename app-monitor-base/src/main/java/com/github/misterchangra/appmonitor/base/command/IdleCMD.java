package com.github.misterchangra.appmonitor.base.command;

import com.github.misterchangra.appmonitor.base.command.result.SystemInfoResut;
import com.github.misterchangra.appmonitor.base.util.TextUtil;

import java.util.regex.Pattern;

/**
 * 空闲指令
 *
 * 用于保持链接的活跃性
 */
public class IdleCMD extends BaseCommand<SystemInfoResut> {
    @Override
    public SystemInfoResut getResult() {
        this.result = new StringBuilder("top - 11:24:45 up 327 days, 14:08,  1 user,  load average: 0.36, 0.35, 0.28\n" +
                "Threads: 439 total,   1 running, 438 sleeping,   0 stopped,   0 zombie\n" +
                "%Cpu(s):  2.9 us,  5.7 sy,  0.0 ni, 88.6 id,  0.0 wa,  0.0 hi,  2.9 si,  0.0 st\n" +
                "MiB Mem :   7541.0 total,    843.2 free,   1802.7 used,   4895.2 buff/cache\n" +
                "MiB Swap:      0.0 total,      0.0 free,      0.0 used.   5154.9 avail Mem");

        if(this.result == null) {
            return null;
        }


        SystemInfoResut res = new SystemInfoResut();
        if(this.getSystem() == SYSTEM.LINUX) {
            String[] lines = this.result.toString().split("\n");

            for (int i = 0; i < lines.length; i++) {
                this.readLine(lines[i], i, res);
            }
        }

        return res;
    }


    public static void main(String[] args) {
        new IdleCMD().getResult();
    }
    static Pattern line1 =  Pattern.compile(".*load average: (.+?), (.+?),.*");
    static Pattern line2 =  Pattern.compile(".*Threads: (.+?) total,.*");
    static Pattern line3 =  Pattern.compile(".*%Cpu\\(s\\):  (.+?) us,  (.+?) sy.*");
    static Pattern line4 =  Pattern.compile(".*MiB Mem :   (.+?) total,    (.+?) free,   (.+?) used.*");


    private void readLine(String line, int lineNumber, SystemInfoResut res) {
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
    public String getCommand() {
        switch (getSystem()) {
            case LINUX:
                return "pwd";
            case WINDOWS:
                return "cd";
        }
        return "";
    }
}
