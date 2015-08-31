package com.ipr.iprcrm.integration.integrations.servicebus.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by os on 8/21/2015.
 */
public class OpportunityData extends CRMMobileDataModel {
    public String Subject;
    public String Description;
    public double Amount;
    public Date CloseDate;
    public double Probability;
    public String SalesStage;
    public Reference Account;
    public List<Reference> Contacts;


}
