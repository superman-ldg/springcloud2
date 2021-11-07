package com.ldg.cloud.service;

import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyIRule extends RandomRule {
    @Override
    protected int chooseRandomInt(int serverCount) {
        return super.chooseRandomInt(serverCount);
    }
}
