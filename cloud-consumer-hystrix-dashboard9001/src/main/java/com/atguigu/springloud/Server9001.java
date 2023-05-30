package com.atguigu.springloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class Server9001 {
    public static void main(String[] args) {
        SpringApplication.run(Server9001.class, args);
    }

}
