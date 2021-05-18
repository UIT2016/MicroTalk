package com.microtalk.rpcregistercenter.service;
/**
 *
 *
 *
 * 服务注册接口
 **/
public interface ServiceRegister {
 /**
 * 注册中心初始化
 **/
 void init();
 /**
  *服务注册
  * @param serverName server name
  * @param port port
  *
  **/
void register(String serverName,String port);

}
