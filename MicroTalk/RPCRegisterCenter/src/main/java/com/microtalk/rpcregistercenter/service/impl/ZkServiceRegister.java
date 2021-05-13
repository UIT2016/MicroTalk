package com.microtalk.rpcregistercenter.service.impl;

import com.microtalk.rpcregistercenter.common.constant.ZkConstants;
import com.microtalk.rpcregistercenter.service.ServiceRegister;
import jdk.nashorn.internal.runtime.GlobalConstants;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author 812
 * @description: 服务注册
 * @date 2021-05-13 15:08:11
 */
public class ZkServiceRegister  implements ServiceRegister {
private ZooKeeper zooKeeper =null;

    @Override
    public void init() {
        //todo 以后再添加watcher机制
        try {
            zooKeeper=new ZooKeeper(ZkConstants.ZK_HOST,ZkConstants.ZK_SESSIONTIMEOUT,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(String serviceName, String serviceAddress) {

    }
}
