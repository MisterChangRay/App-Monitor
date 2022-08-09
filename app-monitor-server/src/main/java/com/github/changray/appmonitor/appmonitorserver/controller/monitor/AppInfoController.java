package com.github.changray.appmonitor.appmonitorserver.controller.monitor;

import com.github.changray.appmonitor.appmonitorserver.dao.ServerInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * 应用信息
 * 
 */
@RequestMapping("/app")
@RestController
public class AppInfoController {
    @Autowired
    private ServerInfoDao serverInfoDao;

}
