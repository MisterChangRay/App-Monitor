package com.github.misterchangra.appmonitor.base.command;

import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;

import java.util.ArrayList;
import java.util.List;


/**
 * 通过关键字再进程中进行查找
 */
public class FindInProcessByPortCMD extends BaseCommand<List<FindInProcessCMDResult>> {


    @Override
    public List<FindInProcessCMDResult> getResult(SYSTEM system) {
        if(this.result == null || this.result.toString().equals( "")) {
            return null;
        }

        List<FindInProcessCMDResult> res = new ArrayList();
        String[] split = this.result.toString().split("\n");
        for (String s : split) {
            String[] split1 = s.split("\\s+");
            switch (system) {
                case WINDOWS:
                    res.add(new FindInProcessCMDResult(split1[0], split1[1], split1[4]));
                    break;
                case LINUX:
                    res.add(new FindInProcessCMDResult(null, split1[6].split("/")[0], null));
                    break;
            }

        }

        return res;
    }

    @Override
    public String getCommand(SYSTEM system) {
        switch (system) {
            case LINUX:
                return "netstat -ntpl | grep %s";
            case WINDOWS:
                return "netstat -ano | findstr %s";
        }
        return null;
    }
}
