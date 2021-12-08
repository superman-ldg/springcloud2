package com.ldg.cloud.service;

import com.ldg.cloud.pojo.MyEntriy;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class Provior {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//
//            }
//        });//成功到达时回调
//        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            @Override
//            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//
//            }
//        });//消息失败时回调

        rabbitTemplate.setMandatory(true);

        // 消息返回, yml需要配置 publisher-returns: true
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            System.out.println("消息"+correlationId+" 发送失败"+"应答码：" +replyCode+replyText+exchange+routingKey);
        });

        // 消息确认, yml需要配置 publisher-confirms: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("消息发送到exchange成功,id:"+correlationData);
            } else {
                System.out.println("消息发送到exchange失败,原因:"+cause);
            }
        });
        MyEntriy myEntriy = new MyEntriy();
        myEntriy.setId(1);
        myEntriy.setContext("发送消息的测试成功了");
        rabbitTemplate.convertAndSend("/mayikt_ex","",myEntriy);
    }




}
