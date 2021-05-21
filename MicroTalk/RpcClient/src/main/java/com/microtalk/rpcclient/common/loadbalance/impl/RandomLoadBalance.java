package com.microtalk.rpcclient.common.loadbalance.impl;

import com.microtalk.rpcclient.common.loadbalance.LoadBalance;
import org.apache.commons.collections.CollectionUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 812
 * @description: 随机负载均衡
 * @date 2021-05-21 15:05:54
 */
public class RandomLoadBalance extends LoadBalance {
    static ThreadLocalRandom threadLocalRandom=ThreadLocalRandom.current();
    @Override
    public String choseServiceHost() {
        String serviceFeedBack=null;
        if(CollectionUtils.isNotEmpty(SERVICE_LIST)){

            int index= threadLocalRandom.nextInt(SERVICE_LIST.size());
            serviceFeedBack=SERVICE_LIST.get(index);
        }
        return serviceFeedBack;
    }
}
