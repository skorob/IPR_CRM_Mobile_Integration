package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.ObjectFactory;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

/**
 * Created by os on 8/21/2015.
 */
@Component
public class OpportunityCRMMobileToCRMMessageConverter extends CRMMobileToCRMMessageConverter <Opportunity> {




    public Message convert(Opportunity opportunity) {
        ObjectFactory of = new ObjectFactory();

        OpportunityData opportunityData = opportunity.body.data.get(0);

        Message message = convertToMessage(opportunityData);
        message.setCrystalCorrId(opportunity.header.Id);


        Property corrId = of.createProperty();
        corrId.setName("CORRELATION_ID");
        corrId.setValue(opportunity.header.Id);
        message.getPropertyList().getProperty().add(corrId);

        return message;
    }

    @Override
    protected Map<String, String> getChannelsMapping() {
        throw new UnsupportedOperationException();
    }

    public Message convertToMessage(OpportunityData opportunityData) {
        ObjectFactory of = new ObjectFactory();
        Message message = of.createMessage();
        message.setPropertyList(of.createMessagePropertyList());
        Property subject = of.createProperty();
        subject.setName("OPPTY_NAME");
        subject.setValue(opportunityData.Subject);
        message.getPropertyList().getProperty().add(subject);

        Property description = of.createProperty();
        description.setName("OPPTY_NOTES");
        description.setValue(opportunityData.Description);
        message.getPropertyList().getProperty().add(description);

        Property amount = of.createProperty();
        amount.setName("OPPTY_FORECAST_AMOUNT");
        amount.setValue(String.valueOf(opportunityData.Amount));
        message.getPropertyList().getProperty().add(amount);

        if(opportunityData.CloseDate!=null) {
            Property closeDate = of.createProperty();
            closeDate.setName("OPPTY_CLOSE_DATE");
            Calendar instance = Calendar.getInstance(Locale.getDefault());
            instance.setTime(opportunityData.CloseDate);
            closeDate.setValue(DatatypeConverter.printDate(instance));
            message.getPropertyList().getProperty().add(closeDate);
        }

        Property propability = of.createProperty();
        propability.setName("OPPTY_PROBABILITY");
        propability.setValue(String.valueOf(opportunityData.Probability));
        message.getPropertyList().getProperty().add(propability);

        Property salesStage = of.createProperty();
        salesStage.setName("SALES_STAGE");
        salesStage.setValue(Mappings.getOpportunitySalesStageMappingCRMMobileToCRM().get(opportunityData.SalesStage));
        message.getPropertyList().getProperty().add(salesStage);

        if(opportunityData.Account!=null) {
            Property accountRef = of.createProperty();
            accountRef.setName("INF_COMPANY");
            accountRef.setValue(createRefence(opportunityData.Account));
            message.getPropertyList().getProperty().add(accountRef);
        }

        if(opportunityData.Contacts!=null) {
            for(Reference reference : opportunityData.Contacts) {
                Property contactCRMRef = of.createProperty();
                contactCRMRef.setName("INF_CONTACT");
                contactCRMRef.setValue(createRefence(reference));
                message.getPropertyList().getProperty().add(contactCRMRef);
            }
        }




        Property entType = of.createProperty();
        entType.setName("ENTITY_TYPE");
        entType.setValue("INF_OPPORTUNITY");
        message.getPropertyList().getProperty().add(entType);

        fillData(opportunityData, of, message);

        return message;
    }
}
