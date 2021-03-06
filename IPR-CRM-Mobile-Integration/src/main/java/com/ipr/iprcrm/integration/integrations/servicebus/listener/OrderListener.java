package com.ipr.iprcrm.integration.integrations.servicebus.listener;

import com.google.gson.*;
import com.ipr.iprcrm.integration.integrations.servicebus.Config;
import com.ipr.iprcrm.integration.integrations.servicebus.converters.*;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;
import com.ipr.iprcrm.integration.integrations.servicebus.service.CRMService;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.entity.EntityListMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.qpid.amqp_1_0.jms.BytesMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MessageListener;

@Component
public class OrderListener implements MessageListener {


    @Autowired
    JsonToCRMMobileModelConverter jsonToCRMMobileModelConverter;

    @Autowired
    AccountCRMMobileToCRMMessageConverter accountCRMMobileToCRMMessageConverter;

    @Autowired
    ContactCRMMobileToCRMMessageConverter contactCRMMobileToCRMMessageConverter;

    @Autowired
    OpportunityCRMMobileToCRMMessageConverter opportunityCRMMobileToCRMMessageConverter;

    @Autowired
    ActivityCRMMobileToCRMMessageConverter activityCRMMobileToCRMMessageConverter;

    @Autowired
    LeadCRMMobileToCRMMessageConverter leadCRMMobileToCRMMessageConverter;

    Logger log = LoggerFactory.getLogger(OrderListener.class);

    @Autowired
    CRMService crmService;


    public void onMessage(javax.jms.Message message) {
        String json = null;
        try {
            BytesMessage bytesMessage = (BytesMessage)message;
            byte[] bytes  = new byte[(int)bytesMessage.getBodyLength()];
            bytesMessage.readBytes(bytes);
            String jsonS = new String(bytes,"UTF-8");
            log.info("Azure OUT queue. The message is received  "+jsonS +"");
            Gson gson =  new Gson();
            JsonElement jsonRoot = new JsonParser().parse(jsonS);
            String type = "Lead";
            if(!jsonRoot.isJsonArray()) {
                JsonObject body = (JsonObject)((JsonObject)jsonRoot).get("Body");
                JsonArray data= body.getAsJsonArray("Data");
                if(data.size() == 1) {
                    JsonObject item = (JsonObject) data.get(0);
                    type = ((JsonPrimitive) item.get("__type")).getAsString();
                }
            }







            CRMMobileModel mobileModel = null;
            Message crmMessage = null;
            EntityListMessage entityListMessage = null;
            switch (type) {
                case "Account" :
                    mobileModel = jsonToCRMMobileModelConverter.convert(jsonS, Account.class);
                    crmMessage = accountCRMMobileToCRMMessageConverter.convert((Account)mobileModel);
                    break;

                case "AccountContact" :
                    mobileModel = jsonToCRMMobileModelConverter.convert(jsonS, Contact.class);
                    crmMessage = contactCRMMobileToCRMMessageConverter.convert((Contact)mobileModel);
                    break;

                case "Opportunity" :
                    mobileModel = jsonToCRMMobileModelConverter.convert(jsonS, Opportunity.class);
                    crmMessage = opportunityCRMMobileToCRMMessageConverter.convert((Opportunity)mobileModel);
                    break;
                case "Activity" :
                    mobileModel = jsonToCRMMobileModelConverter.convert(jsonS, Activity.class);
                    crmMessage = activityCRMMobileToCRMMessageConverter.convert((Activity)mobileModel);
                    break;
                case "Lead" :
                    entityListMessage = leadCRMMobileToCRMMessageConverter.convert(jsonRoot);
                    break;
            }

            if(!type.equals("Lead")) {
                crmService.sendAccountMessage(crmMessage);
            } else {
                crmService.sendEntityListMessage(entityListMessage);
            }

            message.acknowledge();
        } catch (Exception e) {
            log.error("Error occured while message is received from Azure Out Queue", e);
            throw new RuntimeException(e);
        } finally {
        }
    }


}
