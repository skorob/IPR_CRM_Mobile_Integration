package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by os on 9/3/2015.
 */
public class Mappings {

    private  static HashBiMap<String, String> accountTypesCRMToCRMMobile = HashBiMap.create();
    static {
        accountTypesCRMToCRMMobile.put("COMPETITOR", "Competitor");
        accountTypesCRMToCRMMobile.put("PARTNER", "Partner");
        accountTypesCRMToCRMMobile.put("CLIENT", "Client");
        accountTypesCRMToCRMMobile.put("RESELLER", "Reseller");
        accountTypesCRMToCRMMobile.put("PROSPECT", "Prospect");
        accountTypesCRMToCRMMobile.put("OTHER", "Other");
    }



    private   static HashBiMap<String, String> accountChannelsMappingCRMMobileToCRM =HashBiMap.create();

    static {
        accountChannelsMappingCRMMobileToCRM.put("Linkedin", "COMPANY_LINKEDIN");
        accountChannelsMappingCRMMobileToCRM.put("Site", "COMPANY_WEB");
        accountChannelsMappingCRMMobileToCRM.put("Email", "EMAIL_ADDRESS");
        accountChannelsMappingCRMMobileToCRM.put("Facebook", "COMPANY_FACEBOOK");
        accountChannelsMappingCRMMobileToCRM.put("Twitter", "COMPANY_TWITTER");
        accountChannelsMappingCRMMobileToCRM.put("Phone", "PHONE_NUMBER");
    }

    private   static BiMap<String, String> activityStatusCRMToCRMMobile = HashBiMap.create();

    static {
        activityStatusCRMToCRMMobile.put("CANCELLED", "Cancelled");
        activityStatusCRMToCRMMobile.put("DONE", "Done");
        activityStatusCRMToCRMMobile.put("OPEN", "Open");
        activityStatusCRMToCRMMobile.put("PLANNED", "Planned");
    }

    private   static BiMap<String, String> activityMailStatusCRMToCRMMobile = HashBiMap.create();

    static {
        activityMailStatusCRMToCRMMobile.put("DRAFT", "Planned");
        activityMailStatusCRMToCRMMobile.put("SENT", "Done");
        activityMailStatusCRMToCRMMobile.put("DELIVERED", "Cancelled");

    }

    private   static Map<String, String> activityTypeCRMToCRMMobile = new HashMap<>();

    static {
        activityTypeCRMToCRMMobile.put("INF_EMAIL_OUT", "Email");
        activityTypeCRMToCRMMobile.put("INF_EMAIL_IN", "Email");
        activityTypeCRMToCRMMobile.put("INF_MEETING", "Meeting");
        activityTypeCRMToCRMMobile.put("INF_PHONE_CALL_IN", "Call");
        activityTypeCRMToCRMMobile.put("INF_PHONE_CALL_OUT", "Call");
        activityTypeCRMToCRMMobile.put("INF_TASK", "Task");
    }

    private  static Map<String, String> activityTypeCRMMobileToCRM = new HashMap<>();

    static {
        activityTypeCRMMobileToCRM.put("Email", "INF_EMAIL_OUT");
        activityTypeCRMMobileToCRM.put("Meeting", "INF_MEETING");
        activityTypeCRMMobileToCRM.put("Call", "INF_PHONE_CALL_IN");
        activityTypeCRMMobileToCRM.put("Task", "INF_TASK");
    }


    private  static HashBiMap<String, String> contactChannelsMappingCRMMobileToCRM = HashBiMap.create ();
    static {
        contactChannelsMappingCRMMobileToCRM.put("Linkedin", "CONTACT_LINKEDIN");
        contactChannelsMappingCRMMobileToCRM.put("Skype", "CONTACT_SKYPE");
        contactChannelsMappingCRMMobileToCRM.put("Phone", "PHONE_NUMBER");
        contactChannelsMappingCRMMobileToCRM.put("Email", "EMAIL_ADDRESS");
        contactChannelsMappingCRMMobileToCRM.put("Facebook", "CONTACT_FACEBOOK");
        contactChannelsMappingCRMMobileToCRM.put("Twitter", "CONTACT_TWITTER");
    }

    private   static BiMap<String, String> opportunitySalesStageMappingCRMToCRMMobile = HashBiMap.create();

    static {
        opportunitySalesStageMappingCRMToCRMMobile.put("IDENITFY_DECISION_MAKERS", "IdenitfyDecisionMakers");
        opportunitySalesStageMappingCRMToCRMMobile.put("KEY_BELIEF_ANALYSIS", "KeyBeliefAnalysis ");
        opportunitySalesStageMappingCRMToCRMMobile.put("LEAD", "Lead");
        opportunitySalesStageMappingCRMToCRMMobile.put("LOST", "Lost");
        opportunitySalesStageMappingCRMToCRMMobile.put("NEEDS_ANALYSIS", "NeedsAnalysis");
        opportunitySalesStageMappingCRMToCRMMobile.put("NEGOTIATION", "Negotiation");
        opportunitySalesStageMappingCRMToCRMMobile.put("PROPOSAL", "Proposal");
        opportunitySalesStageMappingCRMToCRMMobile.put("PROSPECTING", "Prospecting");
        opportunitySalesStageMappingCRMToCRMMobile.put("QUALIFICATION", "Qualification");
        opportunitySalesStageMappingCRMToCRMMobile.put("VALUE_PROPOSITION", "ValueProposition");
        opportunitySalesStageMappingCRMToCRMMobile.put("WON", "Won");

    }

    public static Map<String, String> getCompanyTypesMappingCRMToCRMMobile() {
        return accountTypesCRMToCRMMobile;
    }

    public static Map<String, String> getCompanyTypesMappingCRMMobileToCRM() {
        return accountTypesCRMToCRMMobile.inverse();
    }

    public static Map<String, String> getAccountChannelsMappingCRMMobileToCRM() {
        return accountChannelsMappingCRMMobileToCRM;
    }

    public static Map<String, String> getChannelsMappingCRMToCRMMobile() {
        return accountChannelsMappingCRMMobileToCRM.inverse();
    }

    public static Map<String, String> getActivityTypeMappingCRMMobileToCRM() {
        return activityTypeCRMMobileToCRM;
    }

    public static Map<String, String> getActivityTypeMappingCRMToCRMMobile() {
        return activityTypeCRMToCRMMobile;
    }

    public static Map<String, String> getActivityStatusMappingCRMMobileToCRM() {
        return activityStatusCRMToCRMMobile.inverse();
    }

    public static Map<String, String> getActivityStatusMappingCRMToCRMMobile() {
        return activityStatusCRMToCRMMobile;
    }

    public static Map<String, String> getActivityMailStatusMappingCRMMobileToCRM() {
        return activityMailStatusCRMToCRMMobile.inverse();
    }

    public static Map<String, String> getActivityMailStatusMappingCRMToCRMMobile() {
        return activityMailStatusCRMToCRMMobile;
    }

    public static Map<String, String> getContactChannelsMappingCRMMobileToCRM() {
        return contactChannelsMappingCRMMobileToCRM;
    }

    public static Map<String, String> getContactChannelsMappingCRMToCRMMobile() {
        return contactChannelsMappingCRMMobileToCRM.inverse();
    }

    public static Map<String, String> getOpportunitySalesStageMappingCRMToCRMMobile() {
        return opportunitySalesStageMappingCRMToCRMMobile;
    }

    public static Map<String, String> getOpportunitySalesStageMappingCRMMobileToCRM() {
        return opportunitySalesStageMappingCRMToCRMMobile.inverse();
    }



}
