package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.Account;
import org.springframework.stereotype.Component;

/**
 * Created by os on 8/20/2015.
 */

@Component
public class JsonToCRMMobileModelConverter {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

    public JsonToCRMMobileModelConverter() {

    }

    public <T> T convert(String gson, Class<T> clazz) {
        Gson g = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
        return g.fromJson(gson, clazz);
    }
}
