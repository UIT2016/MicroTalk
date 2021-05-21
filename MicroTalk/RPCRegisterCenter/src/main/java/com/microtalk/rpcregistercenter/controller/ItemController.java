package com.microtalk.rpcregistercenter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import javafx.application.Application;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 812
 * @description: items s
 * @date 2021-05-19 16:17:08
 */
@RestController
@RequestMapping("/items")
public class ItemController {
    @RequestMapping(value="/getItem",method = RequestMethod.GET)
    @ResponseBody
    public String getProduct(){
        Map<Object, Object> map=new HashMap<>();
        map.put("code",200);
        map.put("msg","success");
        return JSON.toJSONString(map);
    }

}
