package com.github.changray.appmonitor.appmonitorserver.controller.monitor;

import com.github.changray.appmonitor.appmonitorserver.dao.AppInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.ServerInfoDao;
import com.github.changray.appmonitor.appmonitorserver.dao.po.AppInfo;
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
import java.util.List;


/**
 *
 * 应用信息
 * 
 */
@RequestMapping("/app")
@RestController
public class AppInfoController {
    @Autowired
    private AppInfoDao appInfoDao;


    /**
     * 查询所有分组
     * @return
     */
    @RequestMapping("/group/list")
    public BaseResult<PaginationDTO<String>> groupList() {
        List<String> all = appInfoDao.groupList();
        return BaseResult.page(all);
    }

    @RequestMapping("/list")
    public BaseResult<PaginationDTO<AppInfo>> list(@RequestParam("group") String group, @RequestParam("keyword") String keyword) {
        List<AppInfo> all = appInfoDao.findAll2(keyword, group);
        return BaseResult.page(all);
    }

    @RequestMapping("/add")
    public BaseResult add(@RequestBody AppInfo appInfo) {
        appInfo.setStatus(1);
        appInfo.setCreateTime(Timestamp.from(Instant.now()));
        appInfoDao.save(appInfo);
        return BaseResult.success();
    }

    @RequestMapping("/edit")
    public BaseResult edit(@RequestBody AppInfo appInfo) {
        appInfoDao.save(appInfo);
        return BaseResult.success();
    }

    @RequestMapping("/del")
    public BaseResult del(@RequestBody AppInfo appInfo) {
        appInfoDao.delete(appInfo);
        return BaseResult.success();
    }

}
