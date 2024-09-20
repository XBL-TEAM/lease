package com.xblteam.lease;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync            //启用异步支持
@MapperScan("com.xblteam.lease.web.app.mapper")
public class WebAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAppApplication.class);
    }
}