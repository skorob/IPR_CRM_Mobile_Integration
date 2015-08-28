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
public class AccountCRMMobileToCRMMessageConverter  extends CRMMobileToCRMMessageConverter <Account> {

    private  static Map<String, String> channelsMapping = new HashMap<>();
    static {
        channelsMapping.put("Linkedin","COMPANY_LINKEDIN");
        channelsMapping.put("Site","COMPANY_WEB");
        channelsMapping.put("Email","EMAIL_ADDRESS");
        channelsMapping.put("Facebook","COMPANY_FACEBOOK");
        channelsMapping.put("Twitter","COMPANY_TWITTER");
    }

    private  static Map<String, String> companyTypes = new HashMap<>();
    static {
        companyTypes.put("Competitor","COMPETITOR");
        companyTypes.put("Partner","PARTNER");
        companyTypes.put("Client","CLIENT");
        companyTypes.put("Reseller","RESELLER");
        companyTypes.put("Prospect","PROSPECT");
        companyTypes.put("Other","OTHER");
    }


    public Message convert(Account account) {
        ObjectFactory of = new ObjectFactory();


        AccountData ad = account.body.data.get(0);

        Message message = convertToMessage(ad);
        message.setCrystalCorrId(account.header.Id);

        Property corrId = of.createProperty();
        corrId.setName("CORRELATION_ID");
        corrId.setValue(account.header.Id);
        message.getPropertyList().getProperty().add(corrId);

        return message;
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
            type.setValue(companyTypes.get("Other"));
        } else {
            type.setValue(companyTypes.get(ad.Type));
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

        fillData(ad, of, message);


        Property entType = of.createProperty();
        entType.setName("ENTITY_TYPE");
        entType.setValue("INF_COMPANY");
        message.getPropertyList().getProperty().add(entType);



        for(Channel ch : ad.channels) {
            Property channel = of.createProperty();
            String channelName = channelsMapping.get(ch.Type);
            if(StringUtils.isNotEmpty(channelName)) {
                channel.setValue(ch.Value);
                channel.setName(channelName);
                message.getPropertyList().getProperty().add(channel);
            }
        }
        return message;
    }
}
