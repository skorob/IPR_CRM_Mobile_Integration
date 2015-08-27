package com.ipr.iprcrm.integration.integrations.servicebus.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by os on 8/21/2015.
 */
public class ActivityData extends CRMMobileDataModel {
    public String Subject;
    public String Description;
    public Date PlannedDate;
    public String Type;
    public String Status;
    public AccountRef Opportunity;
    public AccountRef Contact;
    public AccountRef Employee;



}
