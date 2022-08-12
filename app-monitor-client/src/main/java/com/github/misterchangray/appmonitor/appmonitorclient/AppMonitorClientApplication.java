package com.github.misterchangray.appmonitor.appmonitorclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AppMonitorClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppMonitorClientApplication.class, args);
    }

}
