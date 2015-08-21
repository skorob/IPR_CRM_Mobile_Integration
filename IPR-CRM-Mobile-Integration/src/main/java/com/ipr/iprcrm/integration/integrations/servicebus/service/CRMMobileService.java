package com.ipr.iprcrm.integration.integrations.servicebus.service;

import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * Created by os on 8/21/2015.
 */

@Service
public class CRMMobileService {

    @Autowired
    ConnectionFactory connectionFactory;


    public void send(String gson) throws Exception {
        try {


            Connection connection = connectionFactory.createConnection();
            try {
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                try {

                    Destination dest = new QueueImpl("in");

                    MessageProducer producer = session.createProducer(dest);
                    TextMessage message = session.createTextMessage();

                    message.setText(gson);
                    System.out.println("Sending message: " + message.getText());
                    producer.send(message);
                } finally {
                    session.close();
                }
            } finally {
                connection.close();
            }
            Thread.sleep(10000);
        } finally {
            //orderListenerContainer.stop();
            System.out.println("in");
        }
    }

}
