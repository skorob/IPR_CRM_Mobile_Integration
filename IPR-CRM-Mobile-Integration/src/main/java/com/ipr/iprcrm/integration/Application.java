package com.ipr.iprcrm.integration;

import com.ipr.iprcrm.integration.integrations.servicebus.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.*;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@EnableConfigurationProperties(Config.class)
@Configuration
@EnableAutoConfiguration
@ComponentScan

public class Application extends SpringBootServletInitializer  {

    @Bean
    public Validator localValidatorFactoryBean() {

        return new LocalValidatorFactoryBean();
    }

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}