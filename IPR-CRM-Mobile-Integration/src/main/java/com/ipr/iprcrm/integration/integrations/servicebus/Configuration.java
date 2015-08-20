package com.ipr.iprcrm.integration.integrations.servicebus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Service;

/**
 * Created by os on 8/18/2015.
 */
@Service
public class Configuration {


    @Autowired
    SimpleJmsListenerContainerFactory jmsListenerContainerFactory;

    @Autowired
    SimpleMessageListenerContainer serviceBusOrderMessageListenerContainer;

}
