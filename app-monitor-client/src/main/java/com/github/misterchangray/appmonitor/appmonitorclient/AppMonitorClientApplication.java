package com.github.misterchangray.appmonitor.appmonitorclient;

import com.github.misterchangray.appmonitor.appmonitorclient.service.UDPBroadcastService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppMonitorClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppMonitorClientApplication.class, args);
    }

}
