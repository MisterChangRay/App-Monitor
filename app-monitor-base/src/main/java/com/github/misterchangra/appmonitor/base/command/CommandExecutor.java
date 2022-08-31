package com.github.misterchangra.appmonitor.base.command;

public abstract class CommandExecutor  {

    public abstract BaseCMDResult execute(CommandExecutor.SYSTEM system, String cmd);


    public  enum SYSTEM {
        WINDOWS, LINUX
    }


    public abstract CommandExecutor.SYSTEM getSystem();
}
