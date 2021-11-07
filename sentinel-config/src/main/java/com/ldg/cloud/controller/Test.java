package com.ldg.cloud.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    /**
     * value = "/sentinel/test",
     * 将处理流程交给指定的方法 blockExHandler()处理,此时 blockExHandler() 方法必须和抛出异常的方法在同一个类中，这是一种降级操作blockHandler = "",blockHandlerClass = "",
     * 如果我们希望抛出任何异常都能处理，都能调用默认处理方法，而并非只是 BlockException 异常才调用，此时可以使用fallback = "",fallbackClass = ""
     * defaultFallback我们可以使用 @SentinelResource 注解的 defaultFallback 属性，为一个类指定
     * 一个全局的处理错误的方法，
     * @return
     */

    @GetMapping("/sentinel/test")
    @SentinelResource(value = "/sentinel/test",blockHandler = "",fallback = "")
    public String test(){
        System.out.println("成功了");
        return "sentinel test ";
    }


    @GetMapping("/sentinel/test2")
    @SentinelResource("/sentinel/test2")
    public String test1() {
        System.out.println("吃嘎嘎嘎嘎嘎嘎");
        return "sentinel test1 ";
    }
}
