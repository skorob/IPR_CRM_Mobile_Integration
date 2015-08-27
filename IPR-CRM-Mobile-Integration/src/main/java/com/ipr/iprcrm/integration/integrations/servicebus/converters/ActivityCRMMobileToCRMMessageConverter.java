package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.ObjectFactory;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by os on 8/21/2015.
 */
@Component
public class ActivityCRMMobileToCRMMessageConverter extends CRMMobileToCRMMessageConverter <Activity> {

    private  static Map<String, String> actStatus = ActivityCRMMessageToMobileConverter.actStatus.inverse();
    private  static Map<String, String> actTypes = new HashMap<>();

    static {
            actTypes.put("Email", "INF_EMAIL_OUT" );

            actTypes.put("Meeting","INF_MEETING");
            actTypes.put("Call", "INF_PHONE_CALL_IN");
            actTypes.put("Task", "INF_TASK");
    }



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

    public Message convertToMessage(ActivityData opportunityData) {
        ObjectFactory of = new ObjectFactory();
        Message message = of.createMessage();
        message.setPropertyList(of.createMessagePropertyList());
        Property subject = of.createProperty();
        subject.setName("ACT_SUBJECT");
        subject.setValue(opportunityData.Subject);
        message.getPropertyList().getProperty().add(subject);

        Property description = of.createProperty();
        description.setName("ACT_BODY");
        description.setValue(opportunityData.Description);
        message.getPropertyList().getProperty().add(description);

        if(opportunityData.PlannedDate!=null) {
            Property plannedDate = of.createProperty();
            plannedDate.setName("ACT_TARGET_DATE");

            Calendar plannedDateCalendar = Calendar.getInstance();
            plannedDateCalendar.setTime(opportunityData.PlannedDate);
            plannedDate.setValue(DatatypeConverter.printDate(plannedDateCalendar));
            message.getPropertyList().getProperty().add(plannedDate);
        }

        Property type = of.createProperty();
        type.setName("ENTITY_TYPE");
        type.setValue(actTypes.get(opportunityData.Type));
        message.getPropertyList().getProperty().add(type);

        Property status = of.createProperty();
        status.setName("ACT_STATUS");
        status.setValue(actStatus.get(opportunityData.Status));
        message.getPropertyList().getProperty().add(status);

        if(opportunityData.Opportunity!=null) {
            Property opportunityRef = of.createProperty();
            opportunityRef.setName("INF_OPPORTUNITY");
            opportunityRef.setValue(opportunityData.Opportunity.ExternalId);
            message.getPropertyList().getProperty().add(opportunityRef);
        }

        if(opportunityData.Contact!=null) {
            Property contactRef = of.createProperty();
            contactRef.setName("INF_CONTACT");
            contactRef.setValue(opportunityData.Contact.ExternalId);
            message.getPropertyList().getProperty().add(contactRef);
        }

        if(opportunityData.Employee!=null) {
            Property employeeRef = of.createProperty();
            employeeRef.setName("SYS_USER");
            employeeRef.setValue(opportunityData.Employee.ExternalId);
            message.getPropertyList().getProperty().add(employeeRef);
        }


        Property extId = of.createProperty();
        extId.setName("CRM_ID");
        extId.setValue(opportunityData.externalId);
        message.getPropertyList().getProperty().add(extId);


        Property entType = of.createProperty();
        entType.setName("ENTITY_TYPE");
        entType.setValue("INF_ACTIVITY");
        message.getPropertyList().getProperty().add(entType);

        Property entPk = of.createProperty();
        entPk.setName("SYS_ENTITY_PK");
        entPk.setValue(opportunityData.externalId);
        message.getPropertyList().getProperty().add(entPk);

        return message;
    }
}
