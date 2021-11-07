package com.ldg.cloud.redisUtil;


import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;

public class RedissonLock {

    @Autowired
    Redisson redisson;

    public  void lock(){
        String lock="lock";

        RLock lock1 = redisson.getLock(lock);
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("readwrite");
        RLock rLock = readWriteLock.readLock();
        RLock rLock1 = readWriteLock.writeLock();

        try{
            lock1.lock();
        }finally{
            lock1.unlock();
        }
    }

}
