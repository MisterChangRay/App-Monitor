package com.github.misterchangra.appmonitor.base.dto.message;

/**
 * 一些进程得附加信息
 */
public class MonitorProcessExtractInfo {

    // full path 是否存在
    private boolean pathExists;
    // 使用内存
    private int useMem;


    public boolean isPathExists() {
        return pathExists;
    }

    public void setPathExists(boolean pathExists) {
        this.pathExists = pathExists;
    }

    public int getUseMem() {
        return useMem;
    }

    public void setUseMem(int useMem) {
        this.useMem = useMem;
    }
}
