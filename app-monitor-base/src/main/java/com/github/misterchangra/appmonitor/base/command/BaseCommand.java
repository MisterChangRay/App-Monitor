package com.github.misterchangra.appmonitor.base.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class BaseCommand<T> {
    StringBuilder result;


    public  enum SYSTEM {
        WINDOWS, LINUX
    }

    public abstract T getResult();

    public void setResult(StringBuilder result) {
        this.result = result;
    }

    public abstract String getCommand();

    public  BaseCommand<T> execCmd(String... param) {
        String cmd = this.getCommand();
        String[] cmds = new String[3];
        switch (getSystem()) {
            case WINDOWS:
                cmds = new String[]{"cmd", "/c", String.format(cmd, param)};
                break;
            case LINUX:
                cmds = new String[]{ "/bin/sh", "-c", String.format(cmd, param)};
                break;
        }

        Process proc = null;
        String line = null;
        result = new StringBuilder();

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

        return this;
    }

    public SYSTEM getSystem() {
        String os = System.getProperty("os.name");
        SYSTEM sys = SYSTEM.LINUX;
        if (os != null && os.toLowerCase().startsWith("windows")) {
            //Windows操作系统
            sys = SYSTEM.WINDOWS;
        } else if (os != null && os.toLowerCase().startsWith("linux")) {
            //Linux操作系统
            sys = SYSTEM.WINDOWS;
        }
        return sys;
    }
}
