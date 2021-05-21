package com.microtalk.rpcclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author 812
 * @description: boot
 * @date 2021-05-21 15:39:20
 */
@SpringBootApplication
@ServletComponentScan
@Slf4j
public class RpcClientApp {
    public static void main(String[] args) {
        SpringApplication.run(RpcClientApp.class,args);
        log.info(">>>>>>>{} runnning!",RpcClientApp.class.getSimpleName());
    }
}
