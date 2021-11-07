package com.ldg.cloud.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient(value = "Nacos-provider-9001")
public interface FeigonTest {

    @RequestMapping("/nacos")
    public String getNacos();

}
