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

    public   static BiMap<String, String> activityStatusCRMToCRMMobile = HashBiMap.create();

    static {
        activityStatusCRMToCRMMobile.put("CANCELLED", "Cancelled");
        activityStatusCRMToCRMMobile.put("DONE", "Done");
        activityStatusCRMToCRMMobile.put("OPEN", "Open");
        activityStatusCRMToCRMMobile.put("PLANNED", "Planned");
    }

    public   static Map<String, String> activityTypeCRMToCRMMobile = new HashMap<>();

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
}
