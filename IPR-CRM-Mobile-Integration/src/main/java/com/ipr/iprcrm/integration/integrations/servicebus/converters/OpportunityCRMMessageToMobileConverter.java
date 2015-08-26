package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by os on 8/21/2015.
 */
@Component
public class OpportunityCRMMessageToMobileConverter extends CRMMessageToMobileConverter<Opportunity> {

    public   static BiMap<String, String> companyTypes = HashBiMap.create();

    static {
        companyTypes.put("IDENITFY_DECISION_MAKERS", "IdenitfyDecisionMakers");
        companyTypes.put("KEY_BELIEF_ANALYSIS", "KeyBeliefAnalysis ");
        companyTypes.put("LEAD", "Lead");
        companyTypes.put("LOST", "Lost");
        companyTypes.put("NEEDS_ANALYSIS", "NeedsAnalysis");
        companyTypes.put("NEGOTIATION", "Negotiation");
        companyTypes.put("PROPOSAL", "Proposal");
        companyTypes.put("PROSPECTING", "Prospecting");
        companyTypes.put("QUALIFICATION", "Qualification");
        companyTypes.put("VALUE_PROPOSITION", "ValueProposition");
        companyTypes.put("WON", "Won");

    }

    public Opportunity convert(Message message) {
        Opportunity opportunity = new Opportunity();

        opportunity.body = new OpportunityBody();
        opportunity.body.data = new ArrayList<>();
        OpportunityData opportunityData = new OpportunityData();
        opportunity.body.data.add(opportunityData);

        init(opportunity, message, opportunityData);


        opportunityData.Subject = getPropertyValue(message, "OPPTY_NAME");
        opportunityData.Description = getPropertyValue(message, "OPPTY_NOTES");
        opportunityData.Amount = NumberUtils.toDouble(getPropertyValue(message, "OPPTY_FORECAST_AMOUNT"), 0);
        try {
            opportunityData.CloseDate = DateUtils.parseDate(getPropertyValue(message, "OPPTY_CLOSE_DATE"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        opportunityData.Probability = NumberUtils.toDouble(getPropertyValue(message, "OPPTY_PROBABILITY"), 0);
        opportunityData.SalesStage = companyTypes.get(getPropertyValue(message, "SALES_STAGE"));

        opportunityData.Account = new AccountRef();
        opportunityData.Account.ExternalId =  getPropertyValue(message, "INF_COMPANY");

        opportunityData.Contacts = new ArrayList<>();
        for(Property p : message.getPropertyList().getProperty()) {
            if(p.getName().equals("INF_OPPTY???")) {
                ContactRef cf = new ContactRef();
                cf.ExternalId = p.getValue();
                opportunityData.Contacts.add(cf);
            }
        }

        return opportunity;
    }

    @Override
    public String getOrder() {
        return "1";
    }

    @Override
    public String getType() {
        return "AccountContact";
    }


}