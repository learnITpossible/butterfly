package com.domain.butterfly.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * com.domain.butterfly.entity
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/12/27
 */
@Configuration
//@RefreshScope
public class RefreshableConfig {

    @Value("${spring.mail.admin}")
    private String mailAdmin;

    public String getMailAdmin() {

        return mailAdmin;
    }
}
