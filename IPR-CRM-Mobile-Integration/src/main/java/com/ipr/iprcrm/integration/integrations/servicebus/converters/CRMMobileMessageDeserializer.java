package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by os on 8/28/2015.
 */
public class CRMMobileMessageDeserializer implements com.google.gson.JsonDeserializer<CRMMobileDataModel> {




    @Override
    public CRMMobileDataModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String type = ((JsonPrimitive) ((JsonObject)json).get("__type")).getAsString();
        switch (type) {
            case "Account" :
                return context.deserialize(json, AccountData.class);
            case "AccountContact" :
                return context.deserialize(json, ContactData.class);
            case "Opportunity" :
                return context.deserialize(json, OpportunityData.class);
            case "Activity" :
                return context.deserialize(json, ActivityData.class);
        }
        return null;
    }
}
