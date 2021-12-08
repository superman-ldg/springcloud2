package com.ldg.cloud.config;

import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * 异步asynctempalte检查消息是否被消费了消费
 */
@Component
public class DemoProducer
{
    @Autowired
    private AsyncRabbitTemplate rabbitTemplate;

    /**
     * 定时任务
     */
         @Scheduled(cron ="0 0/1 * * * ?")
         public void sendDemoMs(){
          AsyncRabbitTemplate.RabbitConverterFuture<Object>
                future =rabbitTemplate.convertSendAndReceive("", "", "我是大帅哥");
                 future.addCallback(new ListenableFutureCallback()
            {
              @Override
              public void onFailure(Throwable throwable) {
              }
              @Override
              public void onSuccess(Object o){
              System.out.println("成功 : " + o);
              }
            });
         }
}
