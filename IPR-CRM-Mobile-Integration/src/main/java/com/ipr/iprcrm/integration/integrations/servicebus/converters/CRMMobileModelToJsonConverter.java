package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.Account;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.CRMMobileModel;
import org.springframework.stereotype.Component;

/**
 * Created by os on 8/20/2015.
 */

@Component
public class CRMMobileModelToJsonConverter {

    public CRMMobileModelToJsonConverter() {

    }

    public String convert(CRMMobileModel crmMobileModel) {
        Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
        return g.toJson(crmMobileModel);
    }
}
