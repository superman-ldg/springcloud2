package com.ldg.cloud.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @RequestMapping("/fallback")
    public ResponseEntity fallback(){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("服务暂不可用！！！！");
    }
}
