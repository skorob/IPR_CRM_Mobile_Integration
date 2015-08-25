package com.ipr.iprcrm.integration.integrations.servicebus.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by os on 8/25/2015.
 */
public class CRMMobileDataModel {
    @SerializedName("Channels")
    public List<Channel> channels;
    @SerializedName("__externalId")
    public String externalId;
    @SerializedName("__order")
    public String order;
    @SerializedName("__type")
    public String type;
    public String Id;

}
