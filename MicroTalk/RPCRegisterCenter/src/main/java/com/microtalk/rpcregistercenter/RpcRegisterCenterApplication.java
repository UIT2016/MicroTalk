package com.microtalk.rpcregistercenter;

import com.microtalk.rpcregistercenter.service.impl.ZkServiceDiscovery;
import com.microtalk.rpcregistercenter.service.impl.ZkServiceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 812
 * @description: boot
 * @date 2021-05-18 16:31:39
 */
@SpringBootApplication
public class RpcRegisterCenterApplication {
    private static final String SERVICE_NAME = "fish.com";

    private static final String SERVER_ADDRESS = "localhost:2181";

    public static void main(String[] args) {

        SpringApplication.run(RpcRegisterCenterApplication.class, args);

        ZkServiceRegister registry = new ZkServiceRegister();
        registry.init();
        registry.register(SERVICE_NAME,SERVER_ADDRESS);

        ZkServiceDiscovery discovery = new ZkServiceDiscovery();
        discovery.init();
        discovery.discover(SERVICE_NAME);

        while (true){}

    }

}
