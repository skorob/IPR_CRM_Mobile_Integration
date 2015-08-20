package com.ipr.iprcrm.integration.integrations.servicebus.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MessageListener;

@Component
public class OrderListener implements MessageListener {


    public void onMessage(javax.jms.Message message) {
        String json = null;
        try {
            System.out.println("================ " + message);
            message.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

}
