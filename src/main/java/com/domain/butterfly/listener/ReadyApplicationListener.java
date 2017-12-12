package com.domain.butterfly.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * TODO describe the file
 *
 * @author lijing
 * @version 1.0.0
 * @since 2017/12/12
 */
public class ReadyApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

    }
}
