package com.domain.butterfly.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * com.domain.butterfly.listener
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/12
 */
public class StartedApplicationListener implements ApplicationListener<ContextStoppedEvent> {

    @Override
    public void onApplicationEvent(ContextStoppedEvent event) {

        System.out.println("started...");
    }
}
