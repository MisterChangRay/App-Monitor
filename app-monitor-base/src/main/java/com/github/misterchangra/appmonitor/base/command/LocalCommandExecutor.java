package com.github.misterchangra.appmonitor.base.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LocalCommandExecutor extends CommandExecutor {

    @Override
    public BaseCMDResult execute(CommandExecutor.SYSTEM system, String cmd) {
        String[] cmds = new String[3];
        switch (system) {
            case WINDOWS:
                cmds = new String[]{"cmd", "/c", cmd};
                break;
            case LINUX:
                cmds = new String[]{ "/bin/sh", "-c", cmd};
                break;
        }

        Process proc = null;
        String line = null;
        StringBuilder result = new StringBuilder();

        try {
            proc = Runtime.getRuntime().exec(cmds);
            InputStream stdIn = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdIn);
            BufferedReader br = new BufferedReader(isr);

            while ((line = br.readLine()) != null)
                result.append(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new BaseCMDResult(result, true, "success");
    }

    @Override
    public SYSTEM getSystem() {
        String os = System.getProperty("os.name");
        CommandExecutor.SYSTEM sys = CommandExecutor.SYSTEM.LINUX;
        if (os != null && os.toLowerCase().startsWith("windows")) {
            //Windows操作系统
            sys = CommandExecutor.SYSTEM.WINDOWS;
        } else if (os != null && os.toLowerCase().startsWith("linux")) {
            //Linux操作系统
            sys = CommandExecutor.SYSTEM.WINDOWS;
        }
        return sys;
    }


}
