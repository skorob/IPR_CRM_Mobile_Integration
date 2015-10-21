package com.ipr.iprcrm.integration.integrations.servicebus.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    Logger log = LoggerFactory.getLogger(CRMMobileService.class);


    public void send(String gson) throws Exception {
        try {

            log.info("Open Connection ...");
            Connection connection = connectionFactory.createConnection();
            try {
                log.info("Open Session ...");
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                try {

                    Destination dest = new QueueImpl("in");
                    log.info("Create  Producer ...");
                    MessageProducer producer = session.createProducer(dest);
                    log.info("Create  Message ...");
                    BytesMessage message = session.createBytesMessage();
                    message.writeBytes(gson.getBytes());

                    log.info("Sending message to CRMMobile AZURE Queue: " +gson);
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
           // System.out.println("in");
        }
    }

}
