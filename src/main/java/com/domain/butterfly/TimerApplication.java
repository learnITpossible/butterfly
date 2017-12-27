package com.domain.butterfly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * com.domain.butterfly
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/11/10
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TimerApplication {

    public static void main(String[] args) {

        SpringApplication.run(TimerApplication.class, args);
    }
}
