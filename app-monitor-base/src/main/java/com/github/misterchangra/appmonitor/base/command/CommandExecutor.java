package com.github.misterchangra.appmonitor.base.command;

public abstract class CommandExecutor  {

    public abstract BaseCMDResult execute(CommandExecutor.SYSTEM system, String cmd);


    public  enum SYSTEM {
        WINDOWS, LINUX
    }


    public CommandExecutor.SYSTEM getSystem() {
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
