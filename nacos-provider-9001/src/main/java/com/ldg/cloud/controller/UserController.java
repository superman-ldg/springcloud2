package com.ldg.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.ldg.cloud.pojo.User;
import com.ldg.cloud.service.UserService;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    int count=0;
    @Autowired
    private UserService userService;

    @Value("${server.port}")
    private String serverPort;

    @Value("server.port")
    private String port;
    @RequestMapping("/nacos")
    @SentinelResource(value = "aa",blockHandler = "fallBack",fallback = "fall")
    public String getNacos(){
        return "Hello Nacos Discovery"+serverPort;
    }


    //降级异常处理
    public String fallBack(int id, BlockException e){
        if(e instanceof FlowException){
            return "当前服务已被流控! "+e.getClass().getCanonicalName();
        }
        return "当前服务已被降级处理! "+e.getClass().getCanonicalName();
    }
    //异常处理
    public String fall(int id){
        return "当前服务已不可用!";
    }


    /**
     * HystrixCommand的commandProperties配置@HistrixProperty隔离策略
     * Hystrix的隔离策略两种： 分别是线程隔离和信号量隔离
     * 如果不加@HystrixCommand的commandProperties=@HystrixProperty注解配置，
     * 那么：restTemplate.getForObject（）请求是一个线程；@HystrixCommand（）是一个隔离线程。
     *
     * 加上@HystrixCommand的commandProperties=@HystrixProperty注解配置后，将2个线程合并到一个线程里。
     *
     * #@HystrixCommand(fallbackMethod = "selectByIdFallbackHandler", commandProperties = {
     *             // 是否启用服务熔断
     * #            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
     *             // 请求阈值
     *  #           @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
     *             // 时间窗口
     *  #           @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
     *             // 错误比率
     *  #           @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
     *     })
     * @Bean
     *     public ServletRegistrationBean getServlet(){
     *         HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
     *         ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
     *         registrationBean.setLoadOnStartup(1);
     *         registrationBean.addUrlMappings("/actuator/hystrix.stream");
     *         registrationBean.setName("HystrixMetricsStreamServlet");
     *         return registrationBean;
     *     }
     */

    @RequestMapping("/get/all1")
         public List<User> getAll11(){

          // int i=10/0;
          // Thread.sleep(5000);
        count++;
        if(count%2==0) {int i=10/0;}
        System.out.println(count+"----------调用8001的服务----------"+port+"------------");
        return userService.getAll();
    }

    @RequestMapping("/get/all")
    public List<User> getAll() throws InterruptedException {

        Thread.sleep(5000);
        // int i=10/0;
        // Thread.sleep(5000);
       // count++;
        //if(count%2==0) {int i=10/0;}
        System.out.println(count+"----------调用8001的服务----------"+port+"------------");
        return userService.getAll();
    }


    public List<User> timeOut(){
        User user = new User();
        user.setId(404);
        user.setName("8001服务器内部错误");
        System.out.println("---------------8001服务器出现错误，服务器超时---------------");
        List<User> list=new ArrayList<>();
        list.add(user);
        return list;
    }

}
