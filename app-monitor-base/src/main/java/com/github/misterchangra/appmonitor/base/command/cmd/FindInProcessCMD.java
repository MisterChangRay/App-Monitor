package com.github.misterchangra.appmonitor.base.command.cmd;

import com.github.misterchangra.appmonitor.base.command.BaseCommand;
import com.github.misterchangra.appmonitor.base.command.CommandExecutor;
import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;
import com.github.misterchangra.appmonitor.base.util.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 通过关键字再进程中进行查找
 */
public class FindInProcessCMD extends BaseCommand<FindInProcessCMDResult> {


    public FindInProcessCMD(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @Override
    public FindInProcessCMDResult getResult() {
        if(this.executeResult == null) {
            return null;
        }
        if(!this.executeResult.isSuccess()) {
            return null;
        }

        StringBuilder result = this.executeResult.getData();

        CommandExecutor.SYSTEM system = this.getSystem();
        List<FindInProcessCMDResult.ProcessResult> res = new ArrayList();
        String[] split = result.toString().split("\n");


        Pattern compile = null;
        if(system == CommandExecutor.SYSTEM.LINUX) {
            compile = Pattern.compile("(.+?)\\s+?(\\d+)\\s+.+?\\s+?(\\d+)\\s+?(\\d+)\\s+?\\S+\\s+?\\S+\\s+?\\S+\\s+?\\S+\\s+?(.*)");
        }
        for (String s : split) {
            if(system == CommandExecutor.SYSTEM.LINUX) {
                Matcher matcher = TextUtil.match0(s, compile);
                if(matcher.find()) {
                    if(matcher.group(5).startsWith("grep") || matcher.group(5).startsWith("bash")) {
                        continue;
                    }
                    res.add(new FindInProcessCMDResult.ProcessResult(matcher.group(5), matcher.group(2), matcher.group(3)));
                }
            }

            if(system == CommandExecutor.SYSTEM.WINDOWS) {

            }

        }

        return new FindInProcessCMDResult(res);
    }

    @Override
    public String getCommand(CommandExecutor.SYSTEM system) {
        switch (system) {
            case LINUX:
                return "ps -aux | grep %s";
            case WINDOWS:
                return "tasklist | findstr %s";
        }
        return null;
    }
}
