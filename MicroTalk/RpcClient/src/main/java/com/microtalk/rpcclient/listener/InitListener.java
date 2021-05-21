package com.microtalk.rpcclient.listener;

import com.microtalk.rpcclient.common.loadbalance.LoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import javax.net.ssl.SSLEngineResult;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author 812
 * @description: 服务发现监听器，发现请求之后，自动在注册中心发现服务列表
 * @date 2021-05-19 16:46:17
 */
@Slf4j
@WebListener
public class InitListener implements ServletContextListener {
    private static String BASE_SERVICE ;

    private static String SERVICE_NAME ;

    private static String zKHost;

    private ZooKeeper zooKeeper;
static{
    Properties properties = new Properties();
    try {
        properties.load(new FileInputStream("classpath:ZkContent.properties"));
    } catch (IOException e) {
        log.error(">>>>>>",e);
    }
    BASE_SERVICE=properties.getProperty("zookeeper.basename");
    SERVICE_NAME=properties.getProperty("zookeeper.servicename");
    zKHost=properties.getProperty("zk.host");
}

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        init();
    }
    public void init(){
        try {
            zooKeeper = new ZooKeeper(zKHost,5000, (watchedEvent -> {
                if(watchedEvent.getType()== Watcher.Event.EventType.NodeChildrenChanged && watchedEvent.getPath().equals(BASE_SERVICE+SERVICE_NAME)){
                    updateServerList();
                }
            }));
            updateServerList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateServerList(){
        List<String> newServiceList = new ArrayList<>();
        try {
            List <String> children = zooKeeper.getChildren(BASE_SERVICE+SERVICE_NAME,true);
            for(String subNode:children){
                byte [] data = zooKeeper.getData(BASE_SERVICE + SERVICE_NAME +"/" + subNode,false,null);
                String host = new String(data,"utf-8");
                System.out.println("host："+host);
                newServiceList.add(host);
            }
            LoadBalance.SERVICE_LIST = newServiceList;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
