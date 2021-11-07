package com.ldg.cloud.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        Subject subject=new SubjectImpl();
        InvocationHandler subjectProxy=new SubjectProxy(subject);

        Subject o = (Subject)Proxy.newProxyInstance(subjectProxy.getClass().getClassLoader(), subject.getClass().getInterfaces(), subjectProxy);

        o.hello("梁登光");

    }
}
