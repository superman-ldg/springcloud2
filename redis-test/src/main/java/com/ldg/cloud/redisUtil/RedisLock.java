package com.ldg.cloud.redisUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisLock {

       
        private StringRedisTemplate redisTemplate;

        @Autowired
        ValueOperations<String, Object> valueOperations;

        @Autowired
        RedisUtils redisUtils;
        
        @Autowired
        RedisLock(StringRedisTemplate redisTemplate){
            this.redisTemplate=redisTemplate;
        }

        //命令setnx + expire 分开写
        //问题如果获取锁的线程挂了了，则锁永远不会被释放
        public void lock01(){
            if(valueOperations.setIfAbsent("key","true")){
                redisUtils.expire("key",100);
                System.out.println("获得了锁");
                redisUtils.del("key");
            }
        }

        /**
         * 加锁
         * @param key
         * @param value 当前时间 + 超时时间
         * @return
         */
        //setnx + value 值是过期时间
        //问题1.过期时间是客户端生成的，分布式环境下每个客户端必须同步时间
        //   2.没有保存拥有者的唯一标识，可能被其它的客户解锁
        //   3.并发的时候，每个客户都设置过期时间，都getset(key,time),最终只能一个客户加锁成功，但过期时间可能被其它用户覆盖
        public boolean lock(String key, String value){
            if (redisTemplate.opsForValue().setIfAbsent(key, value)){
                return true;
            }
            //解决死锁，且当多个线程同时来时，只会让一个线程拿到锁
            String currentValue = redisTemplate.opsForValue().get(key);
            //如果过期
            if (!StringUtils.isEmpty(currentValue) &&
                    Long.parseLong(currentValue) < System.currentTimeMillis()){
                //获取上一个锁的时间
                String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
                if (StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                    return true;
                }
            }
            return false;
        }

        //set ex px nx
       //问题 1.锁过期释放了，业务还没有完成
      // 锁被其它线程删除
       public  void lock02(){
            if(valueOperations.setIfAbsent("key","lock",100, TimeUnit.SECONDS)){
                Object key = valueOperations.get("key");

            }
    }

    //set ex px nx +校验唯一随机值再删除
    static String luaScript =
            "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        // lua脚本，用来释放分布式锁
    public  void lock03(){
        if(valueOperations.setIfAbsent("key","user_request_id",100, TimeUnit.SECONDS)){

            //非原子性
            if("user_request_id".equals(valueOperations.get("key"))){
                redisUtils.del("key");
                Integer execute = redisTemplate.execute(new DefaultRedisScript<>(luaScript, Integer.class), Arrays.asList("key"), "key");
            }
        }
    }




        /**
         * 解锁
         * @param key
         * @param value
         */
        public void unlock(String key, String value){

            try {
                String currentValue = redisTemplate.opsForValue().get(key);
                if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)){
                    redisTemplate.opsForValue().getOperations().delete(key);
                }
            }catch (Exception e){
                log.error("【redis锁】解锁失败, {}", e);
            }
        }
    }

/**
 * 模拟秒杀
 */
class SecKillService {

    @Autowired
    RedisLock redisLock;

    //超时时间10s
    private static final int TIMEOUT = 10 * 1000;

    public void secKill(String productId){
        long time = System.currentTimeMillis() + TIMEOUT;
        //加锁
        if (!redisLock.lock(productId, String.valueOf(time))){
            throw new RuntimeException("人太多了，等会儿再试吧~");
        }

        //具体的秒杀逻辑

        //解锁
        redisLock.unlock(productId, String.valueOf(time));
    }
}
