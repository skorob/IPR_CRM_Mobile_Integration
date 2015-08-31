package com.ipr.iprcrm.integration.integrations.servicebus.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by os on 8/21/2015.
 */
public class ContactData extends CRMMobileDataModel {
    public String FirstName;
    public String LastName;
    public String Title;
    public String JobTitle;
    public boolean IsPrimary;
    public String EmployeeCount;
    @SerializedName("Account")
    public Reference accountRef;


}
