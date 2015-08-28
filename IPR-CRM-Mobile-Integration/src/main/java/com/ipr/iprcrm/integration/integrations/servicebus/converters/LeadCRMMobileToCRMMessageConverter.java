package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.google.gson.*;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.ObjectFactory;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;
import com.ipr.pa.policyclient.ws.crystal.schemas.entity.Entity;
import com.ipr.pa.policyclient.ws.crystal.schemas.entity.EntityListMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mapping.support.JsonHeaders;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by os on 8/21/2015.
 */
@Component
public class LeadCRMMobileToCRMMessageConverter {

    JsonToCRMMobileModelConverter jsonToCRMMobileModelConverter = new JsonToCRMMobileModelConverter();


    AccountCRMMobileToCRMMessageConverter accountCRMMobileToCRMMessageConverter = new AccountCRMMobileToCRMMessageConverter();


    ContactCRMMobileToCRMMessageConverter contactCRMMobileToCRMMessageConverter = new ContactCRMMobileToCRMMessageConverter() ;


    OpportunityCRMMobileToCRMMessageConverter opportunityCRMMobileToCRMMessageConverter = new OpportunityCRMMobileToCRMMessageConverter();


    ActivityCRMMobileToCRMMessageConverter activityCRMMobileToCRMMessageConverter = new ActivityCRMMobileToCRMMessageConverter();




    public EntityListMessage convert(JsonElement jsonRoot) {

        com.ipr.pa.policyclient.ws.crystal.schemas.entity.ObjectFactory o = new com.ipr.pa.policyclient.ws.crystal.schemas.entity.ObjectFactory();
        EntityListMessage entityListMessage = o.createEntityListMessage();
        entityListMessage.setExtCorrData(o.createEntityListMessageExtCorrData());

        //entityListMessage.setCrystalCorrId(activity.header.Id);
        entityListMessage.setEntityList(o.createEntityList());



        JsonObject data= (JsonObject)(jsonRoot);
        CRMMobileModel mobileModel = null;

        JsonToCRMMobileModelConverter  c= new JsonToCRMMobileModelConverter();
        Lead lead = c.convert(data.toString(), Lead.class);

        for(CRMMobileDataModel d : lead.body.data) {
            Message crmMessage = null;
            if(d instanceof AccountData) {
                crmMessage = accountCRMMobileToCRMMessageConverter.convertToMessage((AccountData)d);
            }
            if(d instanceof OpportunityData) {
                crmMessage = opportunityCRMMobileToCRMMessageConverter.convertToMessage((OpportunityData)d);
            }
            if(d instanceof ActivityData) {
                crmMessage = activityCRMMobileToCRMMessageConverter.convertToMessage((ActivityData)d);
            }
            if(d instanceof ContactData) {
                crmMessage = contactCRMMobileToCRMMessageConverter.convertToMessage((ContactData)d);
            }

            crmMessage.setCrystalCorrId(lead.header.Id);

            ObjectFactory of = new ObjectFactory();
            Property corrId = of.createProperty();
            corrId.setName("CORRELATION_ID");
            corrId.setValue(lead.header.Id);
            crmMessage.getPropertyList().getProperty().add(corrId);

            Entity entity = convertToEntity(crmMessage);
            entityListMessage.getEntityList().getEntity().add(entity);
        }


//        for(int i=0; i< data.size();i++) {
//            JsonObject item = (JsonObject) data.get(i);
//            String type = ((JsonPrimitive) item.get("__type")).getAsString();
//            String jsonS = item.getAsString();
//            switch (type) {
//                case "Account":
//                    mobileModel = jsonToCRMMobileModelConverter.convert(jsonS, Account.class);
//                    crmMessage = accountCRMMobileToCRMMessageConverter.convert((Account) mobileModel);
//                    break;
//
//                case "AccountContact":
//                    mobileModel = jsonToCRMMobileModelConverter.convert(jsonS, Contact.class);
//                    crmMessage = contactCRMMobileToCRMMessageConverter.convert((Contact) mobileModel);
//                    break;
//
//                case "Opportunity":
//                    mobileModel = jsonToCRMMobileModelConverter.convert(jsonS, Opportunity.class);
//                    crmMessage = opportunityCRMMobileToCRMMessageConverter.convert((Opportunity) mobileModel);
//                    break;
//                case "Activity":
//                    mobileModel = jsonToCRMMobileModelConverter.convert(jsonS, Activity.class);
//                    crmMessage = activityCRMMobileToCRMMessageConverter.convert((Activity) mobileModel);
//                    break;
//
//            }
//        }



        return entityListMessage;
    }

    private Entity convertToEntity(Message crmMessage) {
        com.ipr.pa.policyclient.ws.crystal.schemas.entity.ObjectFactory o = new com.ipr.pa.policyclient.ws.crystal.schemas.entity.ObjectFactory();
        Entity e = o.createEntity();


        List<Property> propList = crmMessage.getPropertyList().getProperty();
        for(Property pMessage : propList) {
            com.ipr.pa.policyclient.ws.crystal.schemas.entity.Property p = o.createProperty();
            p.setValue(pMessage.getValue());
            p.setName(pMessage.getName());
            e.getProperty().add(p);
        }

        return e;
    }
}
