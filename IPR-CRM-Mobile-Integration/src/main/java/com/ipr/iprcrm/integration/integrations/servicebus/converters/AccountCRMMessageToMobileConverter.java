package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by os on 8/21/2015.
 */
@Component
public class AccountCRMMessageToMobileConverter extends CRMMessageToMobileConverter<Account> {

    public Account convert(Message message) {
        Account account = new Account();
        account.header = new Header();

        account.body = new AccountBody();
        account.body.data = new ArrayList<>();
        AccountData accountData = new AccountData();
        account.body.data.add(accountData);

        init(account, message, accountData);

        accountData.Name = getPropertyValue(message,"PERSON_FULL_NAME");
        accountData.Description = getPropertyValue(message,"COMPANY_NOTES");
        accountData.Country = getPropertyValue(message, "PERS_ORIG_COUNTRY");
        accountData.Type = Mappings.getCompanyTypesMappingCRMToCRMMobile().get(getPropertyValue(message, "COMPANY_TYPE"));
        accountData.Industry = getPropertyValue(message,"PERS_COM_INDUSTRY");
        accountData.EmployeeCount = getPropertyValue(message,"COMPANY_EMPL_NUM");

        for(Property p : message.getPropertyList().getProperty()) {
            assignChannel(message, p.getName(), accountData);
        }
        return account;
    }

    @Override
    public String getOrder() {
        return "0";
    }

    @Override
    public String getType() {
        return "Account";
    }

    @Override
    protected Map<String, String> getChannelsMapping() {
        return Mappings.getChannelsMappingCRMToCRMMobile();
    }


}
