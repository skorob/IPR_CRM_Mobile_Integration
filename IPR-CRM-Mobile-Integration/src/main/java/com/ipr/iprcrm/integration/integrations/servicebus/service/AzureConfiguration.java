
package com.ipr.iprcrm.integration.integrations.servicebus.service;

import com.ipr.crystal.commons.builders.EndpointTypesBuilder;
import com.ipr.crystal.config.EndpointTypeConfiguration;
import com.ipr.crystal.model.EndpointTypeEntity;
import com.ipr.iprcrm.integration.integrations.servicebus.Config;
import com.ipr.iprcrm.integration.integrations.servicebus.converters.*;
import com.ipr.iprcrm.integration.integrations.servicebus.listener.DMProcessor;
import com.ipr.iprcrm.integration.integrations.servicebus.listener.EventListener;
import com.ipr.iprcrm.integration.integrations.servicebus.listener.OrderListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;



@Configuration
public class AzureConfiguration {

    Log log = LogFactory.getLog(AzureConfiguration.class);

    @Autowired
    OrderListener orderListener;

    @Autowired
    DMProcessor processor;

    @Autowired
    Config config;


    @Bean
    public ConnectionFactory connectionFactory() {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.amqp_1_0.jms.jndi.PropertiesFileInitialContextFactory");
            env.put("connectionfactory.AZURE_CF", config.getCrmMobileConnectionString());
            Context context = new InitialContext(env);
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
        orderListenerContainer.setClientId("CRM-CRMMobile-Integration");
        orderListenerContainer.setupMessageListener(orderListener);
        orderListenerContainer.setDestinationName("out");
        orderListenerContainer.setDurableSubscriptionName(config.getCrmMobileSubscription());
        orderListenerContainer.setPubSubDomain(true);
        orderListenerContainer.setSessionTransacted(false);
        return orderListenerContainer;
    }
//
//
    @Bean
    public EndpointTypeConfiguration endpointTypeConfiguration() {
        EndpointTypeEntity endpointTypeEntity = new EndpointTypeEntity();
        endpointTypeEntity.setFactory("ConnectionFactory");
        endpointTypeEntity.setFactoryInitial("org.hornetq.jms.client.HornetQJMSConnectionFactory");
        endpointTypeEntity.setProviderUrl("http://192.168.2.223:5445");
        endpointTypeEntity.setSecurityCredentials("guest");
        endpointTypeEntity.setSecurityPrincipal("guest");
        EndpointTypeConfiguration config = EndpointTypesBuilder
                .buildEndPointTypeConfiguration(endpointTypeEntity);

        config.registerQueueProcessor(processor, "Crystal!CRM_TO_MOBILE_INT_SERVICE", 1);

        return config;
    }



    public static void main(String [] a) throws Exception {
        AzureConfiguration c = new AzureConfiguration();
        ConnectionFactory cf = c.connectionFactory();
        try {


            Connection connection = cf.createConnection();
            try {
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                try {

                    Destination dest = new QueueImpl("out");

                    MessageProducer producer = session.createProducer(dest);
                    BytesMessage message = session.createBytesMessage();

                    message.writeBytes("{\"Header\":{\"Id\":\"38346E08-4CF5-4AA0-9E45-08B2BDE9136D\",\"Timestamp\":\"2015-08-21T11:17:28.637+00:00\",\"Source\":\"iprcrmmobile\"},\"Body\":{\"Data\":[{\"Name\":\"IPR\",\"Description\":\"from Service Bus\",\"Country\":null,\"Type\":null,\"Industry\":null,\"EmployeeCount\":null,\"Channels\":[{\"Id\":\"CC37FC72-60C4-4354-8B30-BF1F3F20B493\",\"Type\":\"Email\",\"Value\":\"juris.terauds@ideaportriga.lv\"}],\"__externalId\":null,\"__order\":null,\"__type\":\"Account\",\"Id\":\"38346E08-4CF5-4AA0-9E45-08B2BDE9136D\"}]}}".getBytes("UTF-8"));
                    System.out.println("Sending message: " + message);
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
