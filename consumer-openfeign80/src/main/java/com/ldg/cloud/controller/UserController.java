package com.ldg.cloud.controller;

import com.ldg.cloud.pojo.User;
import com.ldg.cloud.service.UserFeign;
import com.ldg.cloud.service.UserFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
//@DefaultProperties(defaultFallback = "")//全局的降级方法，@HystrixCommand，，，如果加就调用特有的降级方法
public class UserController {

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private UserFeignService userFeignService;

    //private static String URL="http://PROVIDER-8001";
/**
    @RequestMapping("/consumer/get/all")
    @HystrixCommand(fallbackMethod = "error",commandProperties = {
            @HystrixProperty(name = "execution.timeout.enabled",value = "true"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "300")
    })
 */
    @RequestMapping("/consumer/get/all")
    public List<User> getAll(){
       // int i=10/0;
        try {
           // Thread.sleep(3000);
            System.out.println("------------通过OpenFeign访问成功----------");
            System.out.println(userFeign);
            System.out.println(userFeignService);
            return userFeign.getAll();
        }catch (Exception e){
            e.printStackTrace();
        }
      return null;
    }

    public List<User> error(){
        User user = new User();
        user.setName("Controller服务器内部出现错误！");
        List<User> list=new ArrayList<>();
        list.add(user);
        return list;
    }
}
