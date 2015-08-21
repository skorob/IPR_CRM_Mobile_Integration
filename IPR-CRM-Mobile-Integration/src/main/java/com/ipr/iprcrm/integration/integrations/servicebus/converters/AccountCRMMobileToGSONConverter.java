package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.Account;
import org.springframework.stereotype.Component;

/**
 * Created by os on 8/20/2015.
 */

@Component
public class AccountCRMMobileToGSONConverter {

    public AccountCRMMobileToGSONConverter() {

    }

    public String convert(Account account) {
        Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
        return g.toJson(account);
    }
}