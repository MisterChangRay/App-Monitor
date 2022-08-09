package com.github.changray.appmonitor.appmonitorserver.dao;

import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ServerInfoDao extends JpaRepository<ServerInfo, String> {

}
