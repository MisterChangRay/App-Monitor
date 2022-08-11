package com.github.changray.appmonitor.appmonitorserver.dao;

import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServerInfoDao extends JpaRepository<ServerInfo, String> {

    @Query(value = "select * from server_info where remark like :remark%", nativeQuery = true)
    List<ServerInfo> findAll2(@Param(value = "remark") String remark);

    @Query(value = "select * from server_info where ip like %:ip%", nativeQuery = true)
    List<ServerInfo>  findByIp(@Param(value = "ip") String ip);
}
