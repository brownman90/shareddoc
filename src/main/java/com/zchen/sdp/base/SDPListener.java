package com.zchen.sdp.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.servlet.ServletContextEvent;

@SuppressWarnings("unchecked")
public class SDPListener implements ApplicationListener {
    private Log log = LogFactory.getLog(SDPListener.class);

    public void contextDestroyed(ServletContextEvent config) {

    }

    public void onApplicationEvent(ApplicationEvent config) {
        if (config instanceof ContextRefreshedEvent) {
            try {
                ApplicationContext applicationContext = ((ContextRefreshedEvent) config).getApplicationContext();
                BeanFactory.loadWebApplicationContext(applicationContext);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}