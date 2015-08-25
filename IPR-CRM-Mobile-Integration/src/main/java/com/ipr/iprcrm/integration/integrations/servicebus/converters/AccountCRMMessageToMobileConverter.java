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
public class AccountCRMMessageToMobileConverter {

    private  static Map<String, String> channelsMapping = new HashMap<>();


    static {
        channelsMapping.put("COMPANY_LINKEDIN","Linkedin");
        channelsMapping.put("COMPANY_WEB","Site");
        channelsMapping.put("EMAIL_ADDRESS","Email");
        channelsMapping.put("COMPANY_FACEBOOK","Facebook");
        channelsMapping.put("COMPANY_TWITTER","Twitter");
    }
    private  static Map<String, String> companyTypes = new HashMap<>();
    static {
        companyTypes.put("COMPETITOR", "Competitor");
        companyTypes.put("PARTNER", "Partner");
        companyTypes.put("CLIENT", "Client");
        companyTypes.put("RESELLER", "Reseller");
        companyTypes.put("PROSPECT", "Prospect");
        companyTypes.put("OTHER", "Other");
    }



    public Account convert(Message message) {
        Account account = new Account();
        account.header = new Header();
        account.header.Id = message.getCrystalCorrId();
        account.header.Source = "iprcrm";
        account.header.Timestamp = new Date();

        account.body = new AccountBody();
        account.body.data = new ArrayList<>();
        AccountData accountData = new AccountData();
        account.body.data.add(accountData);
        accountData.Name = getPropertyValue(message,"PERSON_FULL_NAME");
        accountData.Description = getPropertyValue(message,"COMPANY_NOTES");
        accountData.Country = getPropertyValue(message,"PERS_ORIG_COUNTRY");
        accountData.Type = companyTypes.get(getPropertyValue(message,"COMPANY_TYPE"));
        accountData.Industry = getPropertyValue(message,"PERS_COM_INDUSTRY");
        accountData.EmployeeCount = getPropertyValue(message,"COMPANY_EMPL_NUM");
        accountData.externalId = getPropertyValue(message, "CRM_ID");
        accountData.order = "0";
        accountData.type = "Account";
        accountData.Id = message.getCrystalCorrId();

        for(Property p : message.getPropertyList().getProperty()) {
            assignChannel(message, p.getName(), accountData);
        }
        return account;
    }

    private void assignChannel(Message message, String channelType, AccountData accountData) {
        String value = getPropertyValue(message,channelType);
        if(StringUtils.isNotEmpty(value)) {

            String mobileChannelType = channelsMapping.get(channelType);
            if(StringUtils.isNotEmpty(mobileChannelType)) {
                if(accountData.channels == null) {
                    accountData.channels = new ArrayList<>();
                }
                Channel channel = new Channel();
                channel.Type = mobileChannelType;
                channel.Value = value;
                accountData.channels.add(channel);
            }

        }
    }

    private String getPropertyValue(Message message, String propName) {
        List<Property> props = message.getPropertyList().getProperty();
        for(Property p : props) {
            if(p.getName().equals(propName)) {
                return  p.getValue();
            }
        }
        return null;
    }

}
