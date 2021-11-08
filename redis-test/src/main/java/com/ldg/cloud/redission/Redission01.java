package com.ldg.cloud.redission;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Redission01 {

    @Autowired
    private static RedissonClient redissonClient=null;
    static {

    }

    public static void init(){
        Config config=new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1");
        redissonClient=Redisson.create(config);
    }

}
