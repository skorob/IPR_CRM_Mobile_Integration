package com.ipr.iprcrm.integration.integrations.servicebus.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by os on 8/28/2015.
 */
public class Lead extends  CRMMobileModel {

    @SerializedName("Body")
    public LeadBody body;
}
