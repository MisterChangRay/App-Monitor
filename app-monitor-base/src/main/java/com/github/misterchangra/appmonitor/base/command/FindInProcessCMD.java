package com.github.misterchangra.appmonitor.base.command;

import com.github.misterchangra.appmonitor.base.command.result.FindInProcessCMDResult;

import java.util.ArrayList;
import java.util.List;


/**
 * 通过关键字再进程中进行查找
 */
public class FindInProcessCMD extends BaseCommand<List<FindInProcessCMDResult>> {


    @Override
    public List<FindInProcessCMDResult> getResult() {
        if(this.result == null) {
            return null;
        }

        List<FindInProcessCMDResult> res = new ArrayList();
        String[] split = this.result.toString().split("\n");
        for (String s : split) {
            String[] split1 = s.split("\\s+");
            switch (this.getSystem()) {
                case WINDOWS:
                    res.add(new FindInProcessCMDResult(split1[0], split1[1], split1[4]));
                    break;
                case LINUX:
                    res.add(new FindInProcessCMDResult(split1[10], split1[1], split1[4]));
                    break;
            }

        }

        return res;
    }

    @Override
    public String getCommand() {
        switch (getSystem()) {
            case LINUX:
                return "ps -aux | grep %s";
            case WINDOWS:
                return "tasklist | findstr %s";
        }
        return null;
    }
}
