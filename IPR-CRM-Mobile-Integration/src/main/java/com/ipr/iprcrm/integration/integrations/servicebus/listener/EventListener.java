package com.ipr.iprcrm.integration.integrations.servicebus.listener;


import com.ipr.iprcrm.integration.integrations.servicebus.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MessageListener;

@Component
public class EventListener implements MessageListener {




    public void onMessage(javax.jms.Message message) {
        String json = null;
        try {
            message.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
