package com.whut.xiaomi_work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class XiaomiWorkApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaomiWorkApplication.class, args);
    }
}
