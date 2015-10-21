package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.ObjectFactory;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by os on 8/21/2015.
 */
@Component
public class AccountCRMMobileToCRMMessageConverter  extends CRMMobileToCRMMessageConverter <Account, AccountData> {

//    public Message convert(Account account) {
//        ObjectFactory of = new ObjectFactory();
//
//
//        AccountData ad = account.body.data.get(0);
//
//        Message message = convertToMessage(ad);
//
//        fillHeaders(account, of, message);
//
//        return message;
//    }



    @Override
    protected Map<String, String> getChannelsMapping() {
        return Mappings.getAccountChannelsMappingCRMMobileToCRM();
    }



    public Message convertToMessage(AccountData ad) {
        ObjectFactory of = new ObjectFactory();
        Message message = of.createMessage();
        message.setPropertyList(of.createMessagePropertyList());

        Property name = of.createProperty();
        name.setName("PERSON_FULL_NAME");
        name.setValue(ad.Name);
        message.getPropertyList().getProperty().add(name);

        Property description = of.createProperty();
        description.setName("COMPANY_NOTES");
        description.setValue(ad.Description);
        message.getPropertyList().getProperty().add(description);

        Property country = of.createProperty();
        country.setName("PERS_ORIG_COUNTRY");
        country.setValue(ad.Country);
        message.getPropertyList().getProperty().add(country);

        Property type = of.createProperty();
        type.setName("COMPANY_TYPE");
        if(StringUtils.isEmpty(ad.Type)) {
            type.setValue(Mappings.getCompanyTypesMappingCRMMobileToCRM().get("Other"));
        } else {
            type.setValue(Mappings.getCompanyTypesMappingCRMMobileToCRM().get(ad.Type));
        }
        message.getPropertyList().getProperty().add(type);

        Property industry = of.createProperty();
        industry.setName("PERS_COM_INDUSTRY");
        industry.setValue(ad.Industry);
        message.getPropertyList().getProperty().add(industry);

        Property emplCount = of.createProperty();
        emplCount.setName("COMPANY_EMPL_NUM");
        emplCount.setValue(ad.EmployeeCount);
        message.getPropertyList().getProperty().add(emplCount);

        fillCommonData(ad, of, message);


        Property entType = of.createProperty();
        entType.setName("ENTITY_TYPE");
        entType.setValue("INF_COMPANY");
        message.getPropertyList().getProperty().add(entType);

        if(ad.Contact!=null) {
            Property contactRef = of.createProperty();
            contactRef.setName("INF_CONTACT");
            contactRef.setValue(createRefence(ad.Contact));
            message.getPropertyList().getProperty().add(contactRef);
        }


        assignChannels(ad, of, message);

        return message;
    }


}
