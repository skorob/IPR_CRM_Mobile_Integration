package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.ObjectFactory;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by os on 8/21/2015.
 */
@Component
public class ContactCRMMobileToCRMMessageConverter extends CRMMobileToCRMMessageConverter <Contact,ContactData> {


//    public Message convert(Contact contact) {
//        ObjectFactory of = new ObjectFactory();
//
//        ContactData cd = contact.body.data.get(0);
//
//        Message message = convertToMessage(cd);
//
//        fillHeaders(contact, of, message);
//
//        return message;
//    }

    @Override
    protected Map<String, String> getChannelsMapping() {
        return Mappings.getContactChannelsMappingCRMMobileToCRM();
    }

    public Message convertToMessage(ContactData cd) {
        ObjectFactory of = new ObjectFactory();
        Message message = of.createMessage();
        message.setPropertyList(of.createMessagePropertyList());
        Property firstName = of.createProperty();
        firstName.setName("CONTACT_FNAME");
        firstName.setValue(cd.FirstName);
        message.getPropertyList().getProperty().add(firstName);


        Property surname = of.createProperty();
        surname.setName("CONTACT_SURNAME");
        surname.setValue(cd.LastName);
        message.getPropertyList().getProperty().add(surname);

        Property title = of.createProperty();
        title.setName("CONTACT_HONORIFIC");
        title.setValue(cd.Title);
        message.getPropertyList().getProperty().add(title);

        Property jobTitle = of.createProperty();
        jobTitle.setName("CONTACT_JOB_TITLE");
        jobTitle.setValue(cd.JobTitle);
        message.getPropertyList().getProperty().add(jobTitle);


        if(cd.IsPrimary) {
            Property isPrimary = of.createProperty();
            isPrimary.setName("CONTACT_IS_PRIMARY");
            isPrimary.setValue(cd.accountRef.ExternalId);
            message.getPropertyList().getProperty().add(isPrimary);
        }

        if(cd.accountRef!=null) {
            Property infCompany = of.createProperty();
            infCompany.setName("INF_COMPANY");
            infCompany.setValue(cd.accountRef.ExternalId);
            message.getPropertyList().getProperty().add(infCompany);
        }

        fillCommonData(cd, of, message);


        Property entType = of.createProperty();
        entType.setName("ENTITY_TYPE");
        entType.setValue("INF_CONTACT");
        message.getPropertyList().getProperty().add(entType);



        assignChannels(cd, of, message);

        return message;
    }


}
