package com.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMqUtils {
    // 得到一个连接的 channel
    static ConnectionFactory factory = new ConnectionFactory();
    public static Channel getChannel() throws Exception{
        // 创建一个连接工厂
        factory.setHost("47.106.132.130");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        return channel;
    }
}
