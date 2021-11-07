package com.ldg.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EruekaServer7001 {
    public static void main(String[] args) {
        SpringApplication.run(EruekaServer7001.class,args);
    }
}
