package com.microtalk.rpcregistercenter.service;
/**
 *
 *
 *
 * 服务注册接口
 **/
public interface ServiceRegister {
 /**
 *注册中心初始化
 * @param
 * @return
 *
 **/
 void init();
 /**
  *服务注册
  * @param
  * @return
  *
  **/
void register(String serviceName,String serviceAddress);

}
