package com.ipr.iprcrm.integration.integrations.servicebus.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by os on 8/20/2015.
 */
public class LeadBody {
    @SerializedName("Data")
    public List<CRMMobileDataModel> data;
}
