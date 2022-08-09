package com.github.changray.appmonitor.appmonitorserver.dao;

import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppInfoDao extends JpaRepository<AppInfo, Long> {
}
