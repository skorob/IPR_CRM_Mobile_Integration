package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.Account;

/**
 * Created by os on 8/20/2015.
 */
public class JsonToCRMMobileAccountConverter {

    public JsonToCRMMobileAccountConverter() {

    }

    public Account convert(String gson) {
        Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        return g.fromJson(gson, Account.class);
    }
}
