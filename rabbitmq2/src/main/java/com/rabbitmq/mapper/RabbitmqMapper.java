package com.rabbitmq.mapper;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqMapper {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendWork() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("queue_work", "测试work模型: " + i);
        }
    }

}
