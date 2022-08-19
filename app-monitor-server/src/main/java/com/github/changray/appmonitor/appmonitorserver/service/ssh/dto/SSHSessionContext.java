package com.github.changray.appmonitor.appmonitorserver.service.ssh.dto;


import com.jcraft.jsch.Session;

public class SSHSessionContext extends SSHConnectInfo {

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
