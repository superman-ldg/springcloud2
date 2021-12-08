package com.ldg.cloud.service;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class MyService {

//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "myQueue", durable = "true"),
//            exchange = @Exchange(value = "auto.exch", ignoreDeclarationExceptions = "true"),
//            key = "orderRoutingKey")
//    )
//    public void processOrder(Order order) {
//
//    }
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue,
//            exchange = @Exchange(value = "auto.exch"),
//            key = "invoiceRoutingKey")
//    )
//    public void processInvoice(String invoice) {
//
//    }
//
//    @RabbitListener(queuesToDeclare = @Queue(name = "${my.queue}", durable = "true"))
//    public String handleWithSimpleDeclare(String data) {
//    return null;
//    }
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "auto.headers", autoDelete = "true",
//                    arguments = @Argument(name = "x-message-ttl", value = "10000",
//                            type = "java.lang.Integer")),
//            exchange = @Exchange(value = "auto.headers", type = ExchangeTypes.HEADERS, autoDelete = "true"),
//            arguments = {
//                    @Argument(name = "x-match", value = "all"),
//                    @Argument(name = "thing1", value = "somevalue"),
//                    @Argument(name = "thing2")
//            })
//    )
//    public String handleWithHeadersExchange(String foo) {
//    return null;
//    }

}