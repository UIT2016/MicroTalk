package com.microtalk.rpcregistercenter.service.impl;
import com.microtalk.rpcregistercenter.service.ServiceRegister;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author 812
 * @description: 服务注册
 * @date 2021-05-13 15:08:11
 */
@Slf4j
public class ZkServiceRegister implements ServiceRegister {

    private static String zkHost;
    private static int zkSessionTimeOut;
    private static String zkParentNodePath;

    @Value("${zookeeper.host}")
    public static void setZkHost(String zkHost) {
        ZkServiceRegister.zkHost = zkHost;
    }
    @Value("${zookeeper.sessiontimeout}")
    public static void setZkSessionTimeOut(int zkSessionTimeOut) {
        ZkServiceRegister.zkSessionTimeOut = zkSessionTimeOut;
    }
    @Value("${zookeeper.parentznodepath}")
    public static void setZkParentNodePath(String zkParentNodePath) {
        ZkServiceRegister.zkParentNodePath = zkParentNodePath;
    }

    private static final CountDownLatch latch = new CountDownLatch(1);


    /**
     * 连接 zookeeper 服务器
     *
     * @return Zookeeper
     */
    private static ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(zkHost, zkSessionTimeOut, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException | InterruptedException e) {
            log.error(new Throwable().getStackTrace()[0].getClassName()+"出现异常>>>>>", e);
        }
        return zk;
    }

    /**
     * 创建节点
     *
     * @param zk
     * @param data
     */
    private static  void createNode(ZooKeeper zk, String data) {
        try {
            byte[] bytes = data.getBytes();
            Stat exits=zk.exists(zkParentNodePath,false);
            if(exits==null){
                String path = zk.create(zkParentNodePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                log.debug("create zookeeper parent node ({} => {})", path, data);
            }
            String path = zk.create(zkParentNodePath+"/child", bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            log.debug("create zookeeper child node ({} => {})", path, data);
            log.info(">>>>服务注册成功");
        } catch (KeeperException | InterruptedException e) {
            log.error("", e);
        }
    }

    @Override
    public void init() throws Exception {

    }

    public static void register(String data) {
        log.info(">>>>>>>>服务端注册节点，地址:{}",data);
        if (data != null) {
            ZooKeeper zk = connectServer();
            if (zk != null) {
                createNode(zk, data);
            }
        }
    }

}
