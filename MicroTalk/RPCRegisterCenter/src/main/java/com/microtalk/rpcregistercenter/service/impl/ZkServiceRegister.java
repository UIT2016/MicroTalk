package com.microtalk.rpcregistercenter.service.impl;
import com.microtalk.rpcregistercenter.common.constant.ZkConstants;
import com.microtalk.rpcregistercenter.service.ServiceRegister;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.*;
import org.springframework.beans.factory.annotation.Value;


/**
 * @author 812
 * @description: 服务注册
 * @date 2021-05-13 15:08:11
 */
@Slf4j
public class ZkServiceRegister  implements ServiceRegister {
    private Watcher watcher;
private ZkClient zk;
@Value("${zookeeper.host}")
private String zkHost;
@Value("${zookeeper.sessiontimeout}")
private int zkSessionTimeOut;
@Value("${zookeeper.parentznodepath}")
private String zkParentNodePath;
@Value("${zookeeper.connectiontimeout}")
private int zkConnectionTimeOut;


    @Override
    public void init() {
        //todo 以后再添加watcher机制
        log.info(">>>>>>zk初始化开始");
        zk =new ZkClient(zkHost,zkSessionTimeOut,zkConnectionTimeOut);
        log.info(">>>>>>zk初始化成功");
    }
/**
 * 向zookeeper中的/servers下创建子节点
 * @param
 * @return
 *
 **/
    @Override
    public void register(String serverName, String serviceAddress) {
        //创建registry节点
        String registryPath=zkParentNodePath;
        if(!zk.exists(registryPath)){
            zk.createPersistent(registryPath);
            log.info(">>>>>>创建zk节点"+registryPath);
        }
        //创建service节点
        String servicePath=registryPath+"/"+serverName;
        if (!zk.exists(servicePath)) {
            zk.createPersistent(servicePath);
           log.info(">>>服务注册:" + servicePath);
        }
        //创建临时address节点
        String addressPath=servicePath+"/address-";
        String addressNode=zk.createEphemeralSequential(addressPath,serviceAddress);
        log.info(">>>>>>创建address临时节点");

    }
}
