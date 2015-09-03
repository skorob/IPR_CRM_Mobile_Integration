package com.ipr.iprcrm.integration.integrations.servicebus.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by os on 8/25/2015.
 */
public class CRMMobileModel <T extends CRMMobileBody> {
    @SerializedName("Header")
    public Header header;

    @SerializedName("Body")
    public T body;


}
