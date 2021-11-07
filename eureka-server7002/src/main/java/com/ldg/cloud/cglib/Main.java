package com.ldg.cloud.cglib;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
            LogInterceptor logInterceptor = new LogInterceptor();
            LogInterceptor logInterceptor2 = new LogInterceptor();
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(UserDao.class);   // 设置超类，cglib是通过继承来实现的
            enhancer.setCallbacks(new Callback[]{logInterceptor, logInterceptor2, NoOp.INSTANCE});   // 设置多个拦截器，NoOp.INSTANCE是一个空拦截器，不做任何处理
            enhancer.setCallbackFilter(new DaoFilter());

            UserDao proxy = (UserDao) enhancer.create();   // 创建代理类
            proxy.select();
            proxy.update();

        }
    }
