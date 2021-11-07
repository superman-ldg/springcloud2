package com.ldg.cloud.controller;


import com.ldg.cloud.pojo.User;
import com.ldg.cloud.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Value("server.port")
    private String port;

    @RequestMapping("/get/all")
    @HystrixCommand(fallbackMethod = "timeOut",
            commandProperties = {
                    // 是否启用服务熔断
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    // 请求阈值
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    // 时间窗口
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    // 错误比率
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "40")})
    public List<User> getAll(){
        System.out.println("----------调用8002的服务----------"+port+"------------");
        return userService.getAll();
    }

    public List<User> timeOut(){
        User user = new User();
        user.setId(404);
        user.setName("8002服务器内部错误");
        System.out.println("---------------8002服务器出现错误，服务器超时---------------");
        List<User> list=new ArrayList<>();
        list.add(user);
        return list;
    }


}