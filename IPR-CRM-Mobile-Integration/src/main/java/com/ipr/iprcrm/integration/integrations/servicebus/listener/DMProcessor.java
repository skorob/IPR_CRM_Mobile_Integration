package com.ipr.iprcrm.integration.integrations.servicebus.listener;

import com.ipr.crystal.commons.procmodels.Processor;
import com.ipr.crystal.model.messaging.CrystalMessage;
import com.ipr.iprcrm.integration.integrations.servicebus.converters.*;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.CRMMobileModel;
import com.ipr.iprcrm.integration.integrations.servicebus.service.CRMMobileService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;

/**
 * Created by os on 8/31/2015.
 */

@Component
public class DMProcessor implements Processor {


    Logger log = LoggerFactory.getLogger(DMProcessor.class);

    @Autowired
    AccountCRMMessageToMobileConverter accountCRMToMobileConverter;

    @Autowired
    ContactCRMMessageToMobileConverter contactCRMMessageToMobileConverter;

    @Autowired
    com.ipr.iprcrm.integration.integrations.servicebus.converters.CRMMobileModelToJsonConverter CRMMobileModelToJsonConverter;

    @Autowired
    OpportunityCRMMessageToMobileConverter opportunityCRMMessageToMobileConverter;

    @Autowired
    ActivityCRMMessageToMobileConverter activityCRMMessageToMobileConverter;

    @Autowired
    CRMMobileService crmMobileService;


        public void processMessage(CrystalMessage message) {
            try {

                log.info("Crystal!CRM_TO_MOBILE_INT_SERVICE. Message is received QueryName ["+message.getQueryName()+"] [ " + message.getPayload() + "]");
                JAXBContext jc = JAXBContext.newInstance(com.ipr.pa.policyclient.ws.crystal.schemas.Message.class);

                Unmarshaller unmarshaller = jc.createUnmarshaller();
                //unmarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                ByteArrayInputStream is = new ByteArrayInputStream(message.getPayload().getBytes("UTF-8"));
                com.ipr.pa.policyclient.ws.crystal.schemas.Message m = (com.ipr.pa.policyclient.ws.crystal.schemas.Message) unmarshaller.unmarshal(is);

                String messageType = message.getQueryName();


                String json = null;
                CRMMobileModel crmMobileModel = null;
                switch (messageType) {
                    case "PUSH_ACCOUNT":
                        crmMobileModel = accountCRMToMobileConverter.convert(m);
                        break;
                    case "PUSH_CONTACT":
                        crmMobileModel = contactCRMMessageToMobileConverter.convert(m);
                        break;
                    case "PUSH_OPPORTUNITY":
                        crmMobileModel = opportunityCRMMessageToMobileConverter.convert(m);
                        break;
                    case "PUSH_ACTIVITY":
                        crmMobileModel = activityCRMMessageToMobileConverter.convert(m);
                        break;
                }

                json = CRMMobileModelToJsonConverter.convert(crmMobileModel);
                crmMobileService.send(json);
            } catch (Exception e) {
                log.error("Error Was occured in DM", e);
                throw new RuntimeException(e);
            }
        }


}
