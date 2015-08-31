package com.ipr.iprcrm.integration.integrations.servicebus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by os on 8/18/2015.
 */

@ConfigurationProperties( value = "integration",prefix="integration")
public class Config {


    private String crmMobileConnectionString;

    private String crmMobileSubscription;


    public String getCrmMobileSubscription() {
        return crmMobileSubscription;
    }

    public void setCrmMobileSubscription(String crmMobileSubscription) {
        this.crmMobileSubscription = crmMobileSubscription;
    }


    public String getCrmMobileConnectionString() {
        return crmMobileConnectionString;
    }

    public void setCrmMobileConnectionString(String crmMobileConnectionString) {
        this.crmMobileConnectionString = crmMobileConnectionString;
    }







//    @Autowired
//    SimpleJmsListenerContainerFactory jmsListenerContainerFactory;
//
//    @Autowired
//    SimpleMessageListenerContainer serviceBusOrderMessageListenerContainer;

}
