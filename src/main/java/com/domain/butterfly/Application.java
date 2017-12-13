package com.domain.butterfly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.domain.butterfly
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/11/10
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        /*SpringApplication app = new SpringApplication(Application.class);
        app.addListeners(new StartedApplicationListener());
        app.addListeners(new RefreshedApplicationListener());
        app.addListeners(new StoppedApplicationListener());
        app.addListeners(new ClosedApplicationListener());
        app.run(args);*/

        SpringApplication.run(Application.class, args);
    }

    /*@Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource reportDataSource() {

        return DataSourceBuilder.create().build();
    }*/
}
