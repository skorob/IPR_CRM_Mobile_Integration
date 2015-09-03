package com.ipr.iprcrm.integration.integrations.servicebus.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by os on 9/3/2015.
 */
public class CRMMobileBody <T extends CRMMobileDataModel > {
    @SerializedName("Data")
    public List<T> data;
}
