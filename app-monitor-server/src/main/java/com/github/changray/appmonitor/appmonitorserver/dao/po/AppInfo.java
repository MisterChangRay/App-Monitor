package com.github.changray.appmonitor.appmonitorserver.dao.po;


import javax.persistence.*;
import java.io.File;
import java.util.Date;

@Table(name = "app_info")
@Entity
public class AppInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="project_group")
    private String projectGroup;

    @Column(name="project_name")
    private String projectName;

    /**
     * 文件全路径, 包含路径+文件名称
     */
    @Column(name="full_file_path")
    private String fullFilePath;

    /**
     * 项目使用端口,多个逗号分开
     */
    @Column(name="port")
    private String port;

    @Column(name="remark")
    private String remark;

    @Column(name="status")
    private int status;

    @Column(name="server_ip")
    private String serverIp;

    @Column(name="process_id")
    private String processId;

    @Column(name="auto_restart")
    private int autoRestart;

    @Column(name="test_cmd")
    private String testCmd;

    @Column(name="start_cmd")
    private String startCmd;

    @Column(name="create_time")
    private Date createTime;
    @Column(name="update_time")
    private Date updateTime;
    @Column(name="last_start_time")
    private Date lastStartTime;

    /**
     * 应用连接方式
     *
     * bit 1: 通过客户端, 需要客户端部署 app_client.jar 包
     * bit 2: 通过ssh进行监听
     */
    @Column(name="comm_type")
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
    @Column(name="scan_type")
    private int scanType;


    public String getTestCmd() {
        return testCmd;
    }

    public void setTestCmd(String testCmd) {
        this.testCmd = testCmd;
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
}
