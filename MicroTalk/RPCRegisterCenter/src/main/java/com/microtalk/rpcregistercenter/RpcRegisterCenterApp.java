package com.microtalk.rpcregistercenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/**
 * @author 812
 * @description: boot
 * @date 2021-05-18 16:31:39
 */
@SpringBootApplication
@Slf4j
public class RpcRegisterCenterApp {
    public static void main(String[] args)  {
        SpringApplication.run(RpcRegisterCenterApp.class, args);
        log.info(">>>>>>>srpingboot start");
    }

}
