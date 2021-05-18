package com.microtalk.rpcregistercenter.service;

public interface ServiceDiscovery {
/**
 * 服务发现
 * @param serviceName service name
 * @return result<String></String>
 *
 **/
String discover(String serviceName);
}
