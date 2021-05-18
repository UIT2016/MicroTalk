package com.microtalk.rpcregistercenter.service.impl;

import com.microtalk.rpcregistercenter.service.ServiceDiscovery;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
/**
 * @author 812
 * @description: Zookeeper服务发现
 * @date 2021-05-17 14:07:15
 */
public class ZkServiceDiscovery implements ServiceDiscovery {
    /**
     * 读多写少的场景，使用copyonwrite
     */
    private final List<String> addressCache = new CopyOnWriteArrayList<>();
    private ZkClient zk;
    @Value("${zookeeper.host}")
    private String zkHost;
    @Value("${zookeeper.sessiontimeout}")
    private int zkSessionTimeOut;
    @Value("${zookeeper.parentznodepath}")
    private String zkParentNodePath;
    @Value("${zookeeper.connectiontimeout}")
    private int zkConnectionTimeOut;

    public void init() {
        zk = new ZkClient(zkHost,
                zkSessionTimeOut,
                zkConnectionTimeOut);
        log.info(">>>>>>连接到zookeeper");

    }

    /**
     * zk服务发
     **/
    @Override
    public String discover(String serviceName) {
        try {
            String servicePath = zkParentNodePath + "/" + serviceName;

            //获取服务节点
            if (!zk.exists(servicePath)) {
                log.error(">>>>>无法找到服务节点");
                throw new RuntimeException(String.format(">>>can't find any service node on path {%s}", servicePath));
            }

            //从本地缓存获取某个服务地址
            String address;
            int addressCacheSize = addressCache.size();
            if (addressCacheSize > 0) {
                if (addressCacheSize == 1) {
                    address = addressCache.get(0);
                } else {
                    address = addressCache.get(ThreadLocalRandom.current().nextInt(addressCacheSize));
                }
                log.info(">>>得到服务节点地址" + address);

                //从zk服务注册中心获取某个服务地址
            } else {
                List<String> addressList = zk.getChildren(servicePath);
                addressCache.addAll(addressList);

                //监听servicePath下的子文件是否发生变化
                zk.subscribeChildChanges(servicePath, (parentPath, currentChilds) -> {
                    log.info(">>>servicePath is changed:" + parentPath);
                    addressCache.clear();
                    addressCache.addAll(currentChilds);
                });

                if (CollectionUtils.isEmpty(addressList)) {
                    log.error(">>>>>无法找到服务节点");
                    throw new RuntimeException(String.format(">>>can't find any address node on path {%s}", servicePath));
                }

                int nodeSize = addressList.size();
                if (nodeSize == 1) {
                    address = addressList.get(0);
                } else {

                    //如果多个，则随机取一个
                    address = addressList.get(ThreadLocalRandom.current().nextInt(nodeSize));
                }
                log.info(">>>get address node:" + address);

            }

            //获取IP和端口号
            String addressPath = servicePath + "/" + address;
            return zk.readData(addressPath);
        } catch (Exception e) {
            log.error(">>> service discovery exception: " + e.getMessage());
            zk.close();
        }

        return null;


    }
}
