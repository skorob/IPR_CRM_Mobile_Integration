package com.ipr.iprcrm.integration.integrations.servicebus.service;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.ipr.crystal.commons.builders.EndpointTypesBuilder;
import com.ipr.crystal.commons.procmodels.Processor;
import com.ipr.crystal.config.EndpointTypeConfiguration;
import com.ipr.crystal.model.EndpointTypeEntity;
import com.ipr.crystal.model.messaging.CrystalMessage;
import com.ipr.iprcrm.integration.integrations.servicebus.converters.*;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.Account;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.CRMMobileModel;
import com.ipr.iprcrm.integration.integrations.servicebus.listener.EventListener;
import com.ipr.iprcrm.integration.integrations.servicebus.listener.OrderListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import sun.misc.Resource;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.Hashtable;



@Configuration
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AzureConfiguration {

    Log log = LogFactory.getLog(AzureConfiguration.class);

    @Autowired
    EventListener eventListener;

    @Autowired
    OrderListener orderListener;

    @Autowired
    AccountCRMMessageToMobileConverter accountCRMToMobileConverter;

    @Autowired
    ContactCRMMessageToMobileConverter contactCRMMessageToMobileConverter;

    @Autowired
    CRMMobileModelToJsonConverter CRMMobileModelToJsonConverter;

    @Autowired
    OpportunityCRMMessageToMobileConverter opportunityCRMMessageToMobileConverter;

    @Autowired
    ActivityCRMMessageToMobileConverter activityCRMMessageToMobileConverter;

    @Autowired
    CRMMobileService crmMobileService;

    @Bean
    public ConnectionFactory connectionFactory() {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.amqp_1_0.jms.jndi.PropertiesFileInitialContextFactory");
            env.put("connectionfactory.AZURE_CF", "amqps://iprcrm:x0Ct3T6IqEZDO32Tft6GUkj0GlAMLbV%2FDRV1mP41oKQ%3D@iprcrmmobile.servicebus.windows.net");
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
        orderListenerContainer.setClientId("OrderListener");
        orderListenerContainer.setupMessageListener(orderListener);
        orderListenerContainer.setDestinationName("out");
        orderListenerContainer.setDurableSubscriptionName("Подписка с оплатой по мере использования");
        orderListenerContainer.setPubSubDomain(true);
        orderListenerContainer.setSessionTransacted(false);
        return orderListenerContainer;
    }


    @Bean
    EndpointTypeConfiguration endpointTypeConfiguration() {
        EndpointTypeEntity endpointTypeEntity = new EndpointTypeEntity();
        endpointTypeEntity.setFactory("ConnectionFactory");
        endpointTypeEntity.setFactoryInitial("org.hornetq.jms.client.HornetQJMSConnectionFactory");
        endpointTypeEntity.setProviderUrl("http://192.168.2.223:5445");
        endpointTypeEntity.setSecurityCredentials("guest");
        endpointTypeEntity.setSecurityPrincipal("guest");
        EndpointTypeConfiguration config = EndpointTypesBuilder
                .buildEndPointTypeConfiguration(endpointTypeEntity);

        config.registerQueueProcessor(new Processor() {

            public void processMessage(CrystalMessage message) {
                try {

                    log.info("Crystal!CRM_TO_MOBILE_INT_SERVICE. Message is received [ "+message.getPayload()+"]");
                    JAXBContext jc = JAXBContext.newInstance(com.ipr.pa.policyclient.ws.crystal.schemas.Message.class);

                    Unmarshaller unmarshaller = jc.createUnmarshaller();
                    //unmarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                    ByteArrayInputStream is = new ByteArrayInputStream(message.getPayload().getBytes("UTF-8"));
                    com.ipr.pa.policyclient.ws.crystal.schemas.Message m = (com.ipr.pa.policyclient.ws.crystal.schemas.Message) unmarshaller.unmarshal(is);

                    String messageType = message.getQueryName();

                    String json = null;
                    CRMMobileModel crmMobileModel = null;
                    switch (messageType) {
                        case "PUSH_ACCOUNT" :
                            crmMobileModel = accountCRMToMobileConverter.convert(m);
                        break;
                        case "PUSH_CONTACT" :
                            crmMobileModel = contactCRMMessageToMobileConverter.convert(m);
                        break;
                        case "PUSH_OPPORTUNITY" :
                            crmMobileModel = opportunityCRMMessageToMobileConverter.convert(m);
                        break;
                        case "PUSH_ACTIVITY" :
                            crmMobileModel = activityCRMMessageToMobileConverter.convert(m);
                            break;
                    }
                    json = CRMMobileModelToJsonConverter.convert(crmMobileModel);
                    crmMobileService.send(json);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Crystal!CRM_TO_MOBILE_INT_SERVICE", 1);

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

                    message.writeBytes(Resources.toString(Resources.getResource("json/Publish-Lead.json"), Charsets.UTF_8).getBytes());
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
