package com.ldg.cloud.service;

import com.ldg.cloud.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@FeignClient(value = "provider8001",fallback = UserFeignService.class)
public interface UserFeign {

    @RequestMapping("/get/all")
    public List<User> getAll();
}

