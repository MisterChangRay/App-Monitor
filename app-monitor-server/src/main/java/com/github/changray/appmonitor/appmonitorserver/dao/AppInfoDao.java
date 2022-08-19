package com.github.changray.appmonitor.appmonitorserver.dao;

import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppInfoDao extends JpaRepository<AppInfo, Long> {

    @Query(value = "select * from app_info where project_group like :group% and (remark like %:keyword% or project_name like %:keyword%)", nativeQuery = true)
    List<AppInfo> findAll2(@Param("keyword") String keyword, @Param("group")String group1);

    @Query(value = "select project_group from app_info", nativeQuery = true)
    List<String> groupList();

    @Query(value = "select * from app_info where server_ip like %:ip%", nativeQuery = true)
    List<AppInfo> findAllByIp(@Param("ip") String ip);


}
