package com.ipr.iprcrm.integration.integrations.servicebus.service;

import com.ipr.crystal.commons.builders.EndpointTypesBuilder;
import com.ipr.crystal.config.EndpointTypeConfiguration;
import com.ipr.crystal.model.EndpointTypeEntity;
import com.ipr.iprcrm.integration.integrations.servicebus.listener.DMProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by os on 8/31/2015.
 */
@Configuration
public class DMConfiguration {

    @Autowired
    DMProcessor processor;

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
}
