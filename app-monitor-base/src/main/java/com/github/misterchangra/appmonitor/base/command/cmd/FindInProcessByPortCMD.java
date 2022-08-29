package com.github.misterchangra.appmonitor.base.command.cmd;

import com.github.misterchangra.appmonitor.base.command.BaseCommand;
import com.github.misterchangra.appmonitor.base.command.CommandExecutor;
import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;

import java.util.ArrayList;
import java.util.List;


/**
 * 通过关键字再进程中进行查找
 */
public class FindInProcessByPortCMD extends BaseCommand<FindInProcessCMDResult> {


    public FindInProcessByPortCMD(CommandExecutor commandExecutor) {
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

        List<FindInProcessCMDResult.ProcessResult> res = new ArrayList();
        String[] split = result.toString().split("\n");
        for (String s : split) {
            String[] split1 = s.split("\\s+");
            switch (this.getSystem()) {
                case WINDOWS:
                    res.add(new FindInProcessCMDResult.ProcessResult(split1[0], split1[1], split1[4]));
                    break;
                case LINUX:
                    res.add(new FindInProcessCMDResult.ProcessResult(null, split1[6].split("/")[0], null));
                    break;
            }
        }

        return new FindInProcessCMDResult(res);
    }

    @Override
    public String getCommand(CommandExecutor.SYSTEM system) {
        switch (system) {
            case LINUX:
                return "netstat -ntpl | grep %s";
            case WINDOWS:
                return "netstat -ano | findstr %s";
        }
        return null;
    }
}
