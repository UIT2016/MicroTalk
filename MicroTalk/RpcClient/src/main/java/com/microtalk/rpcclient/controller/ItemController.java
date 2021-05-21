package com.microtalk.rpcclient.controller;

import com.alibaba.fastjson.JSON;
import com.microtalk.rpcclient.common.loadbalance.LoadBalance;
import com.microtalk.rpcclient.common.loadbalance.impl.RandomLoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 812
 * @description: itemcontroller
 * @date 2021-05-21 15:19:02
 */
@RestController
@Slf4j
@RequestMapping("/items")
public class ItemController {

    private RestTemplate restTemplate = new RestTemplate();

    private LoadBalance loadBalance = new RandomLoadBalance();

    @ResponseBody
    @GetMapping(value = "/item", produces = "application/json")
    public String getItems() {
        String host = loadBalance.choseServiceHost();
        ResponseEntity<String> res =  restTemplate.getForEntity("http://" + host + "/items/getItem", String.class);
        String resStr=res.getBody();
        Map map=JSON.parseObject(resStr,Map.class);
        map.put("host", host);
        return JSON.toJSONString(map);
    }


}
