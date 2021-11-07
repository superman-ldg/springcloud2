package com.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api
public class HelloController {
    @ApiOperation(value = "你好")
    @ResponseBody
    @PostMapping("/hello")
    public String hello(@ApiParam(name = "name", value = "对话人", required = true) String name) {
        return name + ", hello";
    }
}

