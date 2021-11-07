package com.ldg.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class UserController {


    @Autowired
    private RestTemplate restTemplate;

    private static String URL="http://PROVIDER8001";

    @RequestMapping("/consumer/get/all")
    public List getAll(){
        System.out.println("------------通过服务名访问成功----------");
        return restTemplate.getForObject(URL+"/get/all",List.class);
    }
}
