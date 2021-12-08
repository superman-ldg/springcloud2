package com.ldg.cloud.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author Administrator
 */
@Configuration
public class DeadUtil {

    private static String NOMAL_QUEUE="nomal_queue";
    private static String NOMAL_EXCHANGE="nomal_exchange";
    private static String DEAD_QUEUE="dead_queue";
    private static String DEAD_EXCHANGE="dead_exchange";

    @Bean
    public Queue nomalQueue(){
        //设置队列所有消息过期时间
        HashMap<String,Object> args= new HashMap<>();
        args.put("x-message-ttl",10000);
        args.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //args.put("x-dead-letter-routing-key","deal"); 看交换机的模式
        return new Queue(NOMAL_QUEUE,true,true,true,args);
    }

    @Bean
    public Queue deadQueue(){
        //设置队列所有消息过期时间
        return new Queue(DEAD_QUEUE);
    }

    @Bean
    public FanoutExchange nomalExchange(){
        return new FanoutExchange(NOMAL_EXCHANGE,true,false);
    }

    @Bean
    public FanoutExchange deadExchange(){
        return new FanoutExchange(DEAD_EXCHANGE,true,false);
    }

    @Bean
    public Binding bindnomalExchange(Queue nomalQueue, FanoutExchange nomalExchange){
        return BindingBuilder.bind(nomalQueue).to(nomalExchange);
    }
    @Bean
    public Binding binddeadExchange(Queue deadQueue,FanoutExchange deadExchange){
        return BindingBuilder.bind(deadQueue).to(deadExchange);
    }


}
