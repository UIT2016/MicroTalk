package com.microtalk.rpcclient.common.loadbalance;

import java.util.List;

/**
 * @author 812
 * @description: 负载均衡
 * @date 2021-05-21 15:04:32
 */
public abstract class LoadBalance {
    public volatile static List<String> SERVICE_LIST;
    public abstract String choseServiceHost();
}
