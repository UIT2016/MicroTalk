package com.microtalk.rpcregistercenter.listener;

import com.microtalk.rpcregistercenter.service.ServiceRegister;
import com.microtalk.rpcregistercenter.service.impl.ZkServiceRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.spi.ServiceRegistry;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

/**
 * @author 812
 * @description: 初始化时将服务注册到注册中心上
 * @date 2021-05-19 15:04:33
 */
@Slf4j
@WebListener
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = new Properties();
        try {
            log.info(">>>>>>>>开始注册服务");
            properties.load(InitListener.class.getClassLoader().getResourceAsStream("application.properties"));

            String hostAddress = InetAddress.getLocalHost().getHostAddress();

            String po = properties.getProperty("server.port");
            int port = Integer.parseInt(po);

            ZkServiceRegister.register(hostAddress+":"+port);
            log.info(">>>>>>>>注册服务成功！");
        } catch (IOException e) {
            e.printStackTrace();
            log.error(">>>>>"+this.getClass().getSimpleName()+"出现异常，detail information:>>>",e);
        }
    }
}
