package com.microtalk.rpcclient.controller;

import com.alibaba.fastjson.JSON;
import com.microtalk.rpcclient.common.loadbalance.LoadBalance;
import com.microtalk.rpcclient.common.loadbalance.impl.RandomLoadBalance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 812
 * @description: itemcontroller
 * @date 2021-05-21 15:19:02
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    private RestTemplate restTemplate = new RestTemplate();

    private LoadBalance loadBalance = new RandomLoadBalance();

    @ResponseBody
    @GetMapping(value = "/item", produces = "application/json", consumes = "application/json")
    public String getItems() {
        String host = loadBalance.choseServiceHost();
        Map res = (Map) restTemplate.getForEntity("http://" + host + "/product/getProduct", Map.class, Map.class);
        Map<String, Object> map = new HashMap(1);
        map.put("host", host);
        return JSON.toJSONString(map);
    }


}
