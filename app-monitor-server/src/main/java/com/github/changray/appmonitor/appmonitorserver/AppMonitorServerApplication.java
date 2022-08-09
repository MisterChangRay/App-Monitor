package com.github.changray.appmonitor.appmonitorserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AppMonitorServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppMonitorServerApplication.class, args);
    }

}
