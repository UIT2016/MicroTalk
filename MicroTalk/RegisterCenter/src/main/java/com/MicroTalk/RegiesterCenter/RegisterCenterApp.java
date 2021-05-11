package com.MicroTalk.RegiesterCenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 812
 * @description: boot
 * @date 2021-05-11 16:03:40
 */

@SpringBootApplication
@EnableEurekaServer
public class RegisterCenterApp {
    public static void main(String[] args) {
        SpringApplication.run(RegisterCenterApp.class);
    }
}
