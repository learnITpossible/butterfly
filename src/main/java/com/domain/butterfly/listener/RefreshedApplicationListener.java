package com.domain.butterfly.listener;

import org.springframework.boot.Banner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * TODO describe the file
 *
 * @author lijing
 * @version 1.0.0
 * @since 2017/12/12
 */
public class RefreshedApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        System.out.println(context.getApplicationName());
        System.out.println(context.getId());
        Banner banner = (Banner) context.getBean("springBootBanner");
        System.out.println(banner);
        System.out.println("refreshed...");
    }
}
