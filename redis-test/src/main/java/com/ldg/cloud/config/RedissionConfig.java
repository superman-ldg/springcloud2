package com.ldg.cloud.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissionConfig {
    @Bean
    public Redisson redisson(){
        Config config=new Config();
        config.useSingleServer().setAddress("").setDatabase(0);
        return (Redisson) Redisson.create(config);
    }

}
