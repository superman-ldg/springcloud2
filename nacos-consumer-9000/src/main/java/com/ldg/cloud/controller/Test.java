package com.ldg.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.ldg.cloud.openfeign.FeigonTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class Test {

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private FeigonTest feigonTest;


    @RequestMapping("/nacos")
    @SentinelResource(value = "/testOpenFeign",blockHandler = "fallBack",fallback = "fall")
    public String getNacos(){

        String nacos = feigonTest.getNacos();
        return "Hello Nacos Discovery"+serverPort+"--------------"+nacos;
    }


    @GetMapping("/configInfo")
    public String info(){
        return "configInfo";
    }

    //降级异常处理
    public String fallBack(int id, BlockException e){
        if(e instanceof FlowException){
            return "当前服务已被流控! "+e.getClass().getCanonicalName();
        }
        return "当前服务已被降级处理! "+e.getClass().getCanonicalName();
    }
    //异常处理
    public String fall(int id){
        return "当前服务已不可用!";
    }


    /**
     spring:
     cloud:
     nacos:
     discovery:
     server-addr: 47.106.132.130:8848
     sentinel:
     enabled: true
     transport:
     dashboard: 47.106.132.130:8858
     port: 8719

     datasource:
     type: com.alibaba.druid.pool.DruidDataSource
     url: jdbc:mysql://47.106.132.130:3306/cloud?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8
     driver-class-name: com.mysql.jdbc.Driver
     username: root
     password: 123456
     */
}
