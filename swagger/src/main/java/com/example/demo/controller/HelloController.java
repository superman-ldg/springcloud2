package com.example.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags="调试接口")
@RestController
@RequestMapping("/hello")
public class HelloController {
    @ApiOperation(value = "你好")
    @ResponseBody
    @GetMapping("get")
    public String hello(@ApiParam(name = "name", value = "对话人", required = true) String name) {
        return name + ", hello";
    }
}

