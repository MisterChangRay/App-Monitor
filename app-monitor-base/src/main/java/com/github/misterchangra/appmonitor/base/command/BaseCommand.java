package com.github.misterchangra.appmonitor.base.command;

public abstract class BaseCommand<T extends BaseCMDResult> {
    protected BaseCMDResult executeResult;
    protected CommandExecutor commandExecutor;

    public BaseCommand(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }


    public abstract T getResult();

    public abstract String getCommand(CommandExecutor.SYSTEM system);

    public String getCommand() {
        return this.getCommand(this.getSystem());
    }


    /**
     * 执行环境执行
     * @param params
     * @return
     */
    public  void execCmd(String... params) {
        this.executeResult = this.commandExecutor.execute(this.getSystem(), String.format(this.getCommand(this.getSystem()), params));
    }

    public CommandExecutor.SYSTEM getSystem() {
       return this.commandExecutor.getSystem();
    }
}
