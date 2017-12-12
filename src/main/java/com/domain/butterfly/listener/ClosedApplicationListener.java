package com.domain.butterfly.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * com.domain.butterfly.listener
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/12
 */
public class ClosedApplicationListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {

        ApplicationContext context = event.getApplicationContext();
        System.out.println(context.getApplicationName());
        System.out.println(context.getId());
        System.out.println("closed...");
    }
}
