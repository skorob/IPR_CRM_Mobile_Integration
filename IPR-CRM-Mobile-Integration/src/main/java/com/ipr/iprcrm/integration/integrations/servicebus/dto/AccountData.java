package com.ipr.iprcrm.integration.integrations.servicebus.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by os on 8/21/2015.
 */
public class AccountData extends CRMMobileDataModel {
    public String Name;
    public String Description;
    public String Country;
    public String Type;
    public String Industry;
    public String EmployeeCount;
    public Reference Contact;

}
