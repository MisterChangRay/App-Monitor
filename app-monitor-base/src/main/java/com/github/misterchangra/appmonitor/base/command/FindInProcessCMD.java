package com.github.misterchangra.appmonitor.base.command;

import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;
import com.github.misterchangra.appmonitor.base.util.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 通过关键字再进程中进行查找
 */
public class FindInProcessCMD extends BaseCommand<List<FindInProcessCMDResult>> {



    @Override
    public List<FindInProcessCMDResult> getResult(SYSTEM system) {
        if(this.result == null) {
            return null;
        }

        List<FindInProcessCMDResult> res = new ArrayList();
        String[] split = this.result.toString().split("\n");


        Pattern compile = null;
        if(system == SYSTEM.LINUX) {
            compile = Pattern.compile("(.+?)\\s+?(\\d+)\\s+.+?\\s+?(\\d+)\\s+?(\\d+)\\s+?\\S+\\s+?\\S+\\s+?\\S+\\s+?\\S+\\s+?(.*)");
        }
        for (String s : split) {
            if(system == SYSTEM.LINUX) {
                Matcher matcher = TextUtil.match0(s, compile);
                if(matcher.find()) {
                    if(matcher.group(5).startsWith("grep") || matcher.group(5).startsWith("bash")) {
                        continue;
                    }
                    res.add(new FindInProcessCMDResult(matcher.group(5), matcher.group(2), matcher.group(3)));
                }
            }

            if(system == SYSTEM.WINDOWS) {

            }

        }

        return res;
    }

    @Override
    public String getCommand(SYSTEM system) {
        switch (system) {
            case LINUX:
                return "ps -aux | grep %s";
            case WINDOWS:
                return "tasklist | findstr %s";
        }
        return null;
    }
}
