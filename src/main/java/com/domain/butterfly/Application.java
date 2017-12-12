package com.domain.butterfly;

import com.domain.butterfly.listener.ClosedApplicationListener;
import com.domain.butterfly.listener.StartedApplicationListener;
import com.domain.butterfly.listener.StoppedApplicationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * com.domain.butterfly
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/11/10
 */
@SpringBootApplication
@EnableScheduling
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(Application.class);
        app.addListeners(new StartedApplicationListener());
        app.addListeners(new StoppedApplicationListener());
        app.addListeners(new ClosedApplicationListener());
        app.run(args);

        // SpringApplication.run(Application.class, args);
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
