package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by os on 8/21/2015.
 */
@Component
public class ContactCRMMessageToMobileConverter extends CRMMessageToMobileConverter<Contact> {





    public Contact convert(Message message) {
        Contact contact = new Contact();

        contact.body = new ContactBody();
        contact.body.data = new ArrayList<>();
        ContactData contactData = new ContactData();
        contact.body.data.add(contactData);

        init(contact, message, contactData);


        contactData.FirstName = getPropertyValue(message,"CONTACT_FNAME");
        contactData.LastName = getPropertyValue(message, "CONTACT_SURNAME");
        contactData.Title = getPropertyValue(message, "CONTACT_HONORIFIC");
        contactData.JobTitle = getPropertyValue(message, "CONTACT_JOB_TITLE");
        contactData.IsPrimary = StringUtils.isNotEmpty(getPropertyValue(message, "CONTACT_IS_PRIMARY"));

        contactData.accountRef =  this.parseIdToRef(getPropertyValue(message, "INF_COMPANY"));

        for(Property p : message.getPropertyList().getProperty()) {
            assignChannel(message, p.getName(), contactData);
        }
        return contact;
    }

    @Override
    public String getOrder() {
        return "1";
    }

    @Override
    public String getType() {
        return "AccountContact";
    }

    @Override
    protected Map<String, String> getChannelsMapping() {
        return Mappings.getContactChannelsMappingCRMToCRMMobile();
    }


}
