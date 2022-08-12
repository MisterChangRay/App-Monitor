package com.github.misterchangra.appmonitor.base.dto.message;

import java.io.File;
import java.util.Date;

public class MonitorProcessInfo {
    private Long id;

    private String projectGroup;

    private String projectName;

    /**
     * 文件全路径, 包含路径+文件名称
     */
    private String fullFilePath;

    /**
     * 项目使用端口,多个逗号分开
     */
    private String port;

    private String remark;

    private int status;

    private String serverIp;

    private String processId;

    private int autoRestart;

    private String testCmd;

    private String startCmd;

    private Date createTime;
    private Date updateTime;
    private Date lastStartTime;

    /**
     * 应用连接方式
     * 1. 通过客户端, 需要客户端部署 app_client.jar 包
     * 2. 通过ssh进行监听
     */
    private int commType;

    /**
     * 应用s扫描监听方式
     * 1. 通过端口监听
     * 2. 通过文件名来检测
     * 3. 通过自定义命令检测
     *
     * 自定义命令执行，必须返回 true, false.或者 0 1结果
     *
     */
    private int scanType;


    // 进程父目录
    private String processBaseDir;
    // 进程文件名
    private String processFileName;

    public String getProcessBaseDir() {
        if(null != this.processBaseDir) {
            return processBaseDir;
        }

        int i = this.fullFilePath.lastIndexOf(File.separator);
        if(i == -1) {
            throw new RuntimeException("路径配置错误, fullFilePath=" + this.fullFilePath);
        }
        this.processBaseDir = this.fullFilePath.substring(0, i);
        this.processFileName = this.fullFilePath.substring(i);
        return this.processBaseDir;
    }

    public String getProcessFileName() {
        if(null != this.processFileName) {
            return processFileName;
        }

        int i = this.fullFilePath.lastIndexOf(File.separator);

        if(i == -1) {
            throw new RuntimeException("路径配置错误， fullFilePath="+ this.fullFilePath);
        }

        this.processBaseDir = this.fullFilePath.substring(0, i);
        this.processFileName = this.fullFilePath.substring(i);
        return this.processBaseDir;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectGroup() {
        return projectGroup;
    }

    public void setProjectGroup(String projectGroup) {
        this.projectGroup = projectGroup;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFullFilePath() {
        return fullFilePath;
    }

    public void setFullFilePath(String fullFilePath) {
        this.fullFilePath = fullFilePath;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public int getAutoRestart() {
        return autoRestart;
    }

    public void setAutoRestart(int autoRestart) {
        this.autoRestart = autoRestart;
    }

    public String getTestCmd() {
        return testCmd;
    }

    public void setTestCmd(String testCmd) {
        this.testCmd = testCmd;
    }

    public String getStartCmd() {
        return startCmd;
    }

    public void setStartCmd(String startCmd) {
        this.startCmd = startCmd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastStartTime() {
        return lastStartTime;
    }

    public void setLastStartTime(Date lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    public int getCommType() {
        return commType;
    }

    public void setCommType(int commType) {
        this.commType = commType;
    }

    public int getScanType() {
        return scanType;
    }

    public void setScanType(int scanType) {
        this.scanType = scanType;
    }
}
