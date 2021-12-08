package com.ldg.cloud.config;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Administrator
 */
@Configuration
public class RabbitMqConfig {
    private static String EXCHANGE_NAME="/mayikt_ex";
    private static String FANOUT_SMS_QUEUE="fanout_sms_queue";
    private static String FANOUT_EMAIL_QUEUE="fanout_email_queue";
    private static String TTL_QUEUE="fanout_ttl_queue";
    private static String TTL_EXCHANGE="ttl_fanout_exchange";

    /**
     *
     * @return
     * 4个参数1.队列名称，2，是否持久化,3，是否只在当前连接有些，4，是否自动删除
     */
    @Bean
    public Queue ttlQueue(){
        //设置队列所有消息过期时间
        HashMap<String,Object> args= new HashMap<>();
        args.put("x-message-ttl",10000);
        return new Queue(TTL_QUEUE,true,true,true,args);
    }
    @Bean
    public Queue ttlQueueMes(){
        //设置消息过期时间
        return new Queue("message_ttl");
    }
    @Bean
    public Binding bingTTlExchange2(Queue ttlQueueMes,FanoutExchange fanoutTTlExchange){
        return BindingBuilder.bind(ttlQueueMes).to(fanoutTTlExchange);
    }
    @Bean
    public Queue sms(){
        return new Queue(FANOUT_SMS_QUEUE,true);
    }
    @Bean
    public Queue email(){
        return new Queue(FANOUT_EMAIL_QUEUE,true);
    }
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXCHANGE_NAME,true,false);
    }
    @Bean
    public FanoutExchange fanoutTTlExchange(){
        return new FanoutExchange(TTL_EXCHANGE,true,false);}
    @Bean
    public Binding bingTTlExchange(Queue sms,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(sms).to(fanoutExchange);
    }
    @Bean
    public Binding bingSmsExchange(Queue ttlQueue,FanoutExchange fanoutTTlExchange){
        return BindingBuilder.bind(ttlQueue).to(fanoutTTlExchange);
    }
    @Bean
    public Binding bingEmailExchange(Queue email,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(email).to(fanoutExchange);
    }
    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate){
        return asyncRabbitTemplate(rabbitTemplate);
    }

    /**
     * 监听队列的功能
     *
     * SimpleMessageListenerContainer 是 spring 在 RabbitMQ 原生api基础上封装实现的一个消费工具类。
     * 该类非常强大，可以实现：监听单个或多个队列、自动启动、自动声明，
     * 它还支持动态配置，如动态添加监听队列、动态调整并发数等等；基本上对RabbitMQ消费场景这个类都能满足。
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(null);    // 监听的队列
        container.setConcurrentConsumers(1);    // 当前的消费者数量
        container.setMaxConcurrentConsumers(2); // 最大的消费者数量
        container.setDefaultRequeueRejected(false); // 是否重回队列
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 签收模式
        container.setExposeListenerChannel(true);
        container.setConsumerTagStrategy(new ConsumerTagStrategy() {    // 消费端的标签策略
            @Override
            public String createConsumerTag(String queue) { //为每个动态的消费者定义一个tag
                return queue + "_" + UUID.randomUUID().toString();
            }
        });
        container.setMessageListener(null); //设置监听类
        //后置处理器，接收到的消息都添加了Header请求头
        container.setAfterReceivePostProcessors(message -> {
            message.getMessageProperties().getHeaders().put("desc",10);
            return message;
        });
        container.setMessageListener((MessageListener) message -> {
            System.out.println("====接收到消息=====");
            System.out.println(message.getMessageProperties());
            System.out.println(new String(message.getBody()));
        });
        container.setConsumerTagStrategy(queue -> "order_queue_"+(1));
//设置消费者的Arguments
        Map<String, Object> args = new HashMap<>();
        args.put("module","订单模块");
        args.put("fun","发送消息");
        container.setConsumerArguments(args);
        return container;
    }


    /** ======================== 定制一些处理策略 =============================*/
    /**
       * 定制化amqp模版
     *
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
      * ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
      */

     public RabbitTemplate rabbitTemplate() {
         RabbitTemplate rabbitTemplate=new RabbitTemplate();

                 // 消息发送失败返回到队列中, yml需要配置 publisher-returns: true
               rabbitTemplate.setMandatory(true);

               // 消息返回, yml需要配置 publisher-returns: true
               rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
                     String correlationId = message.getMessageProperties().getCorrelationId();
                   System.out.println("消息"+correlationId+" 发送失败"+"应答码：" +replyCode+replyText+exchange+routingKey);
                   });

                // 消息确认, yml需要配置 publisher-confirms: true
               rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
                     if (ack) {
                         System.out.println("消息发送到exchange成功,id:"+correlationData.getId());
                          } else {
                         System.out.println("消息发送到exchange失败,原因:"+cause);
                          }
                    });
              return rabbitTemplate;
           }

}
