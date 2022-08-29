package com.github.misterchangra.appmonitor.base.command.cmd;

import com.github.misterchangra.appmonitor.base.command.BaseCommand;
import com.github.misterchangra.appmonitor.base.command.CommandExecutor;
import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看系统内存使用情况
 */
public class ShowSystemMemCMD extends BaseCommand<FindInProcessCMDResult> {


    public ShowSystemMemCMD(CommandExecutor commandExecutor) {
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
                    res.add(new FindInProcessCMDResult.ProcessResult(split1[10], split1[1], split1[4]));
                    break;
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
