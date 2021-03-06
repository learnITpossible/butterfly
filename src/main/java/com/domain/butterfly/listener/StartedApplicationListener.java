package com.domain.butterfly.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

/**
 * com.domain.butterfly.listener
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/12
 */
public class StartedApplicationListener implements ApplicationListener<ContextStartedEvent> {

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {

        ApplicationContext context = event.getApplicationContext();
        System.out.println(context.getApplicationName());
        System.out.println(context.getId());
        System.out.println("started...");
    }
}
