package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.Activity;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.ActivityData;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.ObjectFactory;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;
import com.ipr.pa.policyclient.ws.crystal.schemas.entity.EntityListMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by os on 8/21/2015.
 */
@Component
public class LeadCRMMobileToCRMMessageConverter {
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



    public EntityListMessage convert(JsonObject jsonRoot) {

        com.ipr.pa.policyclient.ws.crystal.schemas.entity.ObjectFactory o = new com.ipr.pa.policyclient.ws.crystal.schemas.entity.ObjectFactory();
        EntityListMessage entityListMessage = o.createEntityListMessage();
        entityListMessage.setExtCorrData(o.createEntityListMessageExtCorrData());

        //entityListMessage.setCrystalCorrId(activity.header.Id);
        entityListMessage.setPropertyList(o.createPropertyList());


        JsonObject body = (JsonObject)jsonRoot.get("Body");
        JsonArray data= body.getAsJsonArray("Data");

        for(int i=0; i< data.size();i++) {
            JsonObject item = (JsonObject) data.get(0);
            type = ((JsonPrimitive) item.get("__type")).getAsString();
        }




        return entityListMessage;
    }
}
