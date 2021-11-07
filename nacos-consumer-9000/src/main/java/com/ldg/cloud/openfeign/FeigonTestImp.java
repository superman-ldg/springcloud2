package com.ldg.cloud.openfeign;

import org.springframework.stereotype.Component;

@Component
public class FeigonTestImp implements FeigonTest{
    @Override
    public String getNacos() {
        return "错误了";
    }
}
