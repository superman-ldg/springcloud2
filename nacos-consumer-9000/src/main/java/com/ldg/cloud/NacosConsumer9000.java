package com.ldg.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.ldg.cloud.openfeign")
public class NacosConsumer9000 {
    public static void main(String[] args) {
        SpringApplication.run(NacosConsumer9000.class,args);
    }
}
