package com.ldg.cloud.config;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {
    @Bean
    @LoadBalanced
    public RestTemplate get(){
       return  new  RestTemplate();
    }

     @Bean
    public ServerList<Server> ribbonServerList() {
        // return new ConfigurationBasedServerList();
        StaticServerList<Server> staticServerList = new StaticServerList<>((new Server("localhost", 8001)));
        return staticServerList;
    }
}
