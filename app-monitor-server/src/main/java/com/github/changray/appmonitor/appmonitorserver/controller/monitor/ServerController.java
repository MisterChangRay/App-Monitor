package com.github.changray.appmonitor.appmonitorserver.controller.monitor;

import com.github.changray.appmonitor.appmonitorserver.dao.ServerInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.po.ServerInfo;
import com.github.misterchangra.appmonitor.base.dto.BaseResult;
import com.github.misterchangra.appmonitor.base.dto.PaginationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;


/**
 *
 * 服务器信息
 *
 * 所有服务器操作配置入口
 *
 *
 */
@RequestMapping("/server")
@RestController
public class ServerController {
    @Autowired
    private ServerInfoDao serverInfoDao;

    @RequestMapping("/list")
    public BaseResult<PaginationDTO<ServerInfo>> list(@RequestParam("remark") String remark) {
        List<ServerInfo> all = serverInfoDao.findAll2(remark);
        return BaseResult.page(all);
    }

    @RequestMapping("/add")
    public BaseResult add(@RequestBody ServerInfo serverInfo) {
        serverInfo.setStatus(1);
        serverInfo.setCreateTime(Timestamp.from(Instant.now()));
        serverInfoDao.save(serverInfo);
        return BaseResult.success();
    }

    @RequestMapping("/edit")
    public BaseResult edit(@RequestBody ServerInfo serverInfo) {
        serverInfoDao.save(serverInfo);
        return BaseResult.success();
    }

    @RequestMapping("/del")
    public BaseResult del(@RequestBody ServerInfo serverInfo) {
        serverInfoDao.delete(serverInfo);
        return BaseResult.success();
    }

}
