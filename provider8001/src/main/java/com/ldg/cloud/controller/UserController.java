package com.ldg.cloud.controller;

import com.ldg.cloud.pojo.User;
import com.ldg.cloud.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.StringConcatFactory;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    int count=0;
    @Autowired
    private UserService userService;

    @Value("server.port")
    private String port;

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
    @HystrixCommand(fallbackMethod = "timeOut",
            commandProperties = {
            //当HystrixCommand.run()使用SEMAPHORE的隔离策略时，设置最大的并发量
            @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests",value = "10"),
            //表示设置是否在取消任务执行时，中断HystrixCommand.run() 的执行
            @HystrixProperty(name = "execution.isolation.thread.interruptOnCancel",value = "true"),
            //开启超时执行
            @HystrixProperty(name = "execution.timeout.enable",value = "true"),
           //表示设置是否在执行超时时，中断HystrixCommand.run() 的执行
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value ="1000" ),
           //表示设置是否在取消任务执行时，中断HystrixCommand.run() 的执行
            @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout",value = "true"),
           //隔离策略
            @HystrixProperty(name = "execution.isolation.strategy",value = "semaphore"),



             // 是否启用服务熔断
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            // 请求阈值,用来设置在滚动时间窗中，断路器熔断的最小请求数。例如，默认该值为20的时候，如果滚动时间窗（默认10秒）内仅收到19个请求，
           // 即使这19个请求都失败了，断路器也不会打开。
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            // 跳闸后休息的时间窗口
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            // 错误比率超过了就跳闸
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "40"),
            //如果设置true，则强制使断路器跳闸，则会拒绝所有的请求.此值会覆盖circuitBreaker.forceClosed的值
            @HystrixProperty(name = "circuitBreaker.forceOpen",value = "false"),
            //如果设置true，则强制使断路器进行关闭状态，此时会允许执行所有请求，无论是否失败的次数达到circuitBreaker.errorThresholdPercentage值
            @HystrixProperty(name = "circuitBreaker.forceClosed",value = "false"),



            //捕获和HystrixCommand 和 HystrixObservableCommand 执行信息相关的配置属性
            //设置统计滚动窗口的时间长度
            @HystrixProperty(name = "metrics.rollingStats.timeMilliseconds",value = "1000"),
            //设置统计滚动窗口的桶数量
            @HystrixProperty(name = "metrics.rollingStats.numBuckets",value = "10"),
            //设置执行延迟是否被跟踪，并且被计算在失败百分比中。如果设置为false,则所有的统计数据返回-1
            @HystrixProperty(name = "metrics.rollingPercentile.enable",value = "true"),
            //此属性设置统计滚动百分比窗口的持续时间
            @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds",value = "6000"),
            //设置统计滚动百分比窗口的桶数量
            @HystrixProperty(name = "metrics.rollingPercentile.numBuckets",value = "6"),
            //此属性设置每个桶保存的执行时间的最大值。如果桶数量是100，统计窗口为10s，如果这10s里有500次执行，
            // 只有最后100次执行会被统计到bucket里去
            @HystrixProperty(name = "metrics.rollingPercentile.bucketSize",value = "100"),
            //采样时间间隔
            @HystrixProperty(name = "metrics.healthSnapshot.intervalInMilliseconds",value = "500"),

            //Request Context此属性控制HystrixCommand使用到的Hystrix的上下文
            //是否开启请求缓存功能
            @HystrixProperty(name = "requestCache.enable",value = "true"),
             //表示是否开启日志，打印执行HystrixCommand的情况和事件
            @HystrixProperty(name = "requestLog.enable",value = "true"),

            //设置请求合并的属性
            //设置同时批量执行的请求的最大数量
            @HystrixProperty(name = "maxRequestsInBatch",value = "1000"),
            //批量执行创建多久之后，再触发真正的请求
            @HystrixProperty(name = "timerDelayInMilliseconds",value = "10"),
             //是否对HystrixCollapser.execute() 和 HystrixCollapser.queue()开启请求缓存
            @HystrixProperty(name = "requestCache.enable",value = "true"),
    },
            //Thread Pool Properties
            //设置Hystrix Commands的线程池行为，大部分情况线程数量是10。
            threadPoolProperties = {
            //设置线程池的core的大小
            @HystrixProperty(name = "coreSize",value = "10"),
                    //设置最大的线程池的大小，只有设置allowMaximumSizeToDivergeFromCoreSize时，此值才起作用
                    @HystrixProperty(name = "maximumSize",value = "10"),
                    //设置最大的BlockingQueue队列的值。如果设置-1，则使用SynchronousQueue队列，如果设置正数，则使用LinkedBlockingQueue队列
                    @HystrixProperty(name = "maxQueueSize",value = "-1"),
                    //因为maxQueueSize值不能被动态修改，所有通过设置此值可以实现动态修改等待队列长度。
                    // 即等待的队列的数量大于queueSizeRejectionThreshold时（但是没有达到maxQueueSize值）
                    // ，则开始拒绝后续的请求进入队列。
                    @HystrixProperty(name = "queueSizeRejectionThreshold",value = "5"),
                    //设置线程多久没有服务后，需要释放（maximumSize-coreSize ）个线程
                    @HystrixProperty(name = "keepAliveTimeMinutes",value = "1"),
                    //设置allowMaximumSizeToDivergeFromCoreSize值为true时，maximumSize才有作用
                    @HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize",value = "false"),

    })
         public List<User> getAll11(){

          // int i=10/0;
          // Thread.sleep(5000);
        count++;
        if(count%2==0) {int i=10/0;}
        System.out.println(count+"----------调用8001的服务----------"+port+"------------");
        return userService.getAll();
    }

    @ApiOperation(value="获取⽤户列表",	notes="")
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
