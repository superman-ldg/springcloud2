package com.ldg.cloud.proxy;

public class SubjectImpl implements Subject{
    @Override
    public void hello(String param) {
        System.out.println("hello  "+param);
    }
}
