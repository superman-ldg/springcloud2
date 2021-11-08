package com.ldg.cloud.controller;

import com.ldg.cloud.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
@Api(tags="调试接口")
@RestController
@RequestMapping("/hello")
public class HelloController {

    @ApiOperation(value = "一个Rest")
    @RequestMapping(value = "request",method = RequestMethod.GET)
    public User rest(User user){
        return user;
    }

    @ApiOperation(value = "一个Get")
    @GetMapping("get")
    public User get(User user){
        return user;
    }

    @PostMapping("post")
    public User post(User user){
        return user;
    }

    @DeleteMapping("delete")
    public User delete(User user)
    {
        return user;
    }

    @PutMapping ("put")
    public User put(User user)
    {
        return user;
    }
}
