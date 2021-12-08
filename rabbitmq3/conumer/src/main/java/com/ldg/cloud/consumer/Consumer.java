package com.ldg.cloud.consumer;

import com.ldg.cloud.pojo.MyEntriy;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
public class Consumer {

    /**
     * @param message
     * 只有确认了才能再去消费消息
     * 如果没有确认，AMQP不会重新发送。(允许耗时很长)
     * 如果连接断开了，AMQP则会重新发给下一个消费者(可能会重复消费，需要编码保证不被重复消费)
     */
    @RabbitListener(queuesToDeclare= @Queue(name = "fanout_sms_queue"))
    public void getSms(MyEntriy message, Channel channel){
        /** 采用手动应答模式, 手动确认应答更为安全稳定*/
        /**
         * 三个参数，
         * 1.消息的id,
         * 2.是否开启多确认，
         * 3.requeue–如果被拒绝的消息应该被重新查询，而不是丢弃/死信，则为true
         */
       // channel.basicAck();
        /**
         *1.消息的id,
         * 2.requeue–如果被拒绝的消息应该被重新查询，而不是丢弃/死信，则为true
         */
       // channel.basicReject();
        System.out.println("消费者接收到了消息"+message);
    }
    @RabbitListener(
            queuesToDeclare= @Queue(name = "fanout_email_queue") )
    public  void getEmail(MyEntriy message){
        System.out.println("邮件收到了消息"+message);
    }
}
