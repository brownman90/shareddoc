package com.zchen.base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanFactory {

    private static ApplicationContext ctx;

    public static void loadWebApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    public static void loadApplicationContext(String[] xmlFileConfig) {
        ctx = new ClassPathXmlApplicationContext(xmlFileConfig);
    }

    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

}
