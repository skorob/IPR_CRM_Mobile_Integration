package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.ObjectFactory;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by os on 8/21/2015.
 */
@Component
public class ActivityCRMMobileToCRMMessageConverter extends CRMMobileToCRMMessageConverter <Activity> {


    public Message convert(Activity activity) {
        ObjectFactory of = new ObjectFactory();


        ActivityData opportunityData = activity.body.data.get(0);

        Message message = convertToMessage(opportunityData);
        message.setCrystalCorrId(activity.header.Id);


        Property corrId = of.createProperty();
        corrId.setName("CORRELATION_ID");
        corrId.setValue(activity.header.Id);
        message.getPropertyList().getProperty().add(corrId);

        return message;
    }

    @Override
    protected Map<String, String> getChannelsMapping() {
        throw new UnsupportedOperationException();
    }

    public Message convertToMessage(ActivityData activityData) {
        ObjectFactory of = new ObjectFactory();
        Message message = of.createMessage();
        message.setPropertyList(of.createMessagePropertyList());
        Property subject = of.createProperty();
        subject.setName("ACT_SUBJECT");
        subject.setValue(activityData.Subject);
        message.getPropertyList().getProperty().add(subject);

        Property description = of.createProperty();
        description.setName("ACT_BODY");
        description.setValue(activityData.Description);
        message.getPropertyList().getProperty().add(description);

        if(activityData.PlannedDate!=null) {
            Property plannedDate = of.createProperty();
            plannedDate.setName("ACT_TARGET_DATE");

            Calendar plannedDateCalendar = Calendar.getInstance();
            plannedDateCalendar.setTime(activityData.PlannedDate);
            plannedDate.setValue(DatatypeConverter.printDate(plannedDateCalendar));
            message.getPropertyList().getProperty().add(plannedDate);
        }

        Property type = of.createProperty();
        type.setName("ENTITY_TYPE");
        type.setValue(Mappings.getActivityTypeMappingCRMMobileToCRM().get(activityData.Type));
        message.getPropertyList().getProperty().add(type);

        Property status = of.createProperty();
        status.setName("ACT_STATUS");
        status.setValue(Mappings.getActivityStatusMappingCRMMobileToCRM().get(activityData.Status));
        message.getPropertyList().getProperty().add(status);

        if(activityData.Opportunity!=null) {
            Property opportunityRef = of.createProperty();
            opportunityRef.setName("INF_OPPORTUNITY");
            opportunityRef.setValue(createRefence(activityData.Opportunity) );
            message.getPropertyList().getProperty().add(opportunityRef);
        }

        if(activityData.Contact!=null) {
            Property contactRef = of.createProperty();
            contactRef.setName("INF_CONTACT");
            contactRef.setValue(createRefence(activityData.Contact));
            message.getPropertyList().getProperty().add(contactRef);
        }

        if(activityData.Employee!=null) {
            Property employeeRef = of.createProperty();
            employeeRef.setName("SYS_USER");
            employeeRef.setValue(createRefence(activityData.Employee));
            message.getPropertyList().getProperty().add(employeeRef);
        }


        fillData(activityData, of, message);






        return message;
    }


}
