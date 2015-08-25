package com.ipr.iprcrm.integration.integrations.servicebus.service;

import com.ipr.crystal.config.EndpointTypeConfiguration;
import com.ipr.crystal.model.messaging.CrystalMessage;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by os on 8/21/2015.
 */
@Service
public class CRMService {

    Log log = LogFactory.getLog(CRMService.class);

    @Autowired
    EndpointTypeConfiguration endpointTypeConfiguration;

    public void sendAccountMessage(Message message) throws Exception  {
        JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        jaxbMarshaller.marshal(message,stringWriter);

        String crmAccountMessage  = stringWriter.toString();
        CrystalMessage crystalMessage = new CrystalMessage();
        crystalMessage.setSystemName("CRM");
        crystalMessage.setQueryName("SYNC_OBJECTS");
        crystalMessage.setQueryVersion("1.0");
        crystalMessage.setPayload(crmAccountMessage);

        log.info("To CRM : " + crystalMessage);

        endpointTypeConfiguration.send(crystalMessage, "Crystal!CRM_MOBILE_SYS_OUT");


    }
}
