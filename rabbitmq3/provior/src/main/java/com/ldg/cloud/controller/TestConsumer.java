package com.ldg.cloud.controller;


import com.ldg.cloud.service.Provior;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
public class TestConsumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Provior provior;

    @GetMapping("/get")
    public void senMag(){
        
        provior.send();
        System.out.println("+++++++++++++++");
    }
    @GetMapping("/get2")
    public void senMag2(){

        rabbitTemplate.convertAndSend("/mayikt_ex","","comfirmtest！");
        System.out.println("+++++++++++++++");
    }
    @GetMapping("/get3")
    public void senMag3(){

        rabbitTemplate.convertAndSend("ttl_fanout_exchange","","comfirmtest！");
        System.out.println("+++++++++++++++");
    }
    @GetMapping("/get4")
    public void senMag4(){
        MessageProperties props = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setMessageId("123")
                .setHeader("bar", "baz")
                .setExpiration("1000")
                .build();

        MessagePostProcessor messagePostProcessor=new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("1000");
                return message;
            }
        };

        rabbitTemplate.convertAndSend("ttl_fanout_exchange","","comfirmtest！",messagePostProcessor);
        System.out.println("延迟队列");
    }

    @GetMapping("/get5")
    public void senMag5(){
        MessageProperties props = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setMessageId("123")
                .setHeader("bar", "baz")
                .setExpiration("1000")
                .build();

        MessagePostProcessor messagePostProcessor=new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("1000");
                return message;
            }
        };
        rabbitTemplate.convertAndSend("nomal_exchange","","comfirmtest！",messagePostProcessor);
        System.out.println("死信队列");
    }

}
