package com.baizhi.wz.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MyWebAware implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }

    public static Object getBeanByName(String name){
        Object bean = applicationContext.getBean(name);
        return bean;
    }

    public static Object getBeanByClass(Class clazz){
        Object bean = applicationContext.getBean(clazz);
        return bean;
    }
}
