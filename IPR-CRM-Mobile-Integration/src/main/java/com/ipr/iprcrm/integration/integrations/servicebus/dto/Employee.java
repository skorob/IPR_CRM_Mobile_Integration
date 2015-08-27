package com.ipr.iprcrm.integration.integrations.servicebus.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by os on 8/20/2015.
 */
public class Employee extends CRMMobileModel {

    @SerializedName("Body")
    public EmployeeBody body;
}
