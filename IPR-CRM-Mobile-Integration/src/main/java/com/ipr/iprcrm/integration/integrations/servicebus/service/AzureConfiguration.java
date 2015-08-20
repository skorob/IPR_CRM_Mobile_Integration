package com.ipr.iprcrm.integration.integrations.servicebus.service;

import com.ipr.iprcrm.integration.integrations.servicebus.listener.EventListener;
import com.ipr.iprcrm.integration.integrations.servicebus.listener.OrderListener;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;



@Configuration
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AzureConfiguration {


    @Autowired
    EventListener eventListener;

    @Autowired
    OrderListener orderListener;

    public ConnectionFactory connectionFactory() {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.amqp_1_0.jms.jndi.PropertiesFileInitialContextFactory");
            env.put("connectionfactory.AZURE_CF", "amqps://iprcrm:x0Ct3T6IqEZDO32Tft6GUkj0GlAMLbV%2FDRV1mP41oKQ%3D@iprcrmmobile.servicebus.windows.net");
            Context context = new InitialContext(env);
            System.out.println("fff");
            return (ConnectionFactory) context.lookup("AZURE_CF");
        } catch (Exception exc) {
            throw new RuntimeException("Error to init connection factory", exc);
        }
    }

    @Bean
    public SimpleJmsListenerContainerFactory jmsListenerContainerFactory() {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setSubscriptionDurable(true);
        factory.setSessionTransacted(false);
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(connectionFactory());
    }

    @Bean
    public SimpleMessageListenerContainer serviceBusOrderMessageListenerContainer() {
        SimpleMessageListenerContainer orderListenerContainer = new SimpleMessageListenerContainer();
        orderListenerContainer.setConnectionFactory(connectionFactory());
        orderListenerContainer.setClientId("OrderListener");
        orderListenerContainer.setupMessageListener(orderListener);
        orderListenerContainer.setDestinationName("in");
        orderListenerContainer.setDurableSubscriptionName("???????? ? ??????? ?? ???? ?????????????");
        orderListenerContainer.setPubSubDomain(true);
        orderListenerContainer.setSessionTransacted(false);
        return orderListenerContainer;
    }


    public static void main(String [] a) throws Exception {
        AzureConfiguration c = new AzureConfiguration();
        ConnectionFactory cf = c.connectionFactory();
//        SimpleMessageListenerContainer orderListenerContainer = new SimpleMessageListenerContainer();
//        orderListenerContainer.setConnectionFactory(cf);
//        orderListenerContainer.setClientId("OrderListener");
//        orderListenerContainer.setupMessageListener(new OrderListener());
//        orderListenerContainer.setDestinationName("in");
//        orderListenerContainer.setDurableSubscriptionName("???????? ? ??????? ?? ???? ?????????????");
//        orderListenerContainer.setPubSubDomain(true);
//        orderListenerContainer.setSessionTransacted(false);
//        orderListenerContainer.start();
        try {


            Connection connection = cf.createConnection();
            try {
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                try {

                    Destination dest = new QueueImpl("in");

                    MessageProducer producer = session.createProducer(dest);
                    TextMessage message = session.createTextMessage();

                    message.setText("This is message ");
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
