package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by os on 8/21/2015.
 */
@Component
public class ActivityCRMMessageToMobileConverter extends CRMMessageToMobileConverter<Activity> {

    public   static BiMap<String, String> actStatus = HashBiMap.create();

    static {
        actStatus.put("CANCELLED", "Cancelled");
        actStatus.put("DONE", "Done");
        actStatus.put("OPEN", "Open");
        actStatus.put("PLANNED", "Planned");
    }

    public   static Map<String, String> actTypes = new HashMap<>();

    static {
        actTypes.put("INF_EMAIL_OUT", "Email");
        actTypes.put("INF_EMAIL_IN", "Email");
        actTypes.put("INF_MEETING","Meeting");
        actTypes.put("INF_PHONE_CALL_IN","Call");
        actTypes.put("INF_PHONE_CALL_OUT","Call");
        actTypes.put("INF_TASK","Task");
    }

    public Activity convert(Message message) {
        Activity activity = new Activity();

        activity.body = new ActivityBody();
        activity.body.data = new ArrayList<>();
        ActivityData activityData = new ActivityData();
        activity.body.data.add(activityData);

        init(activity, message, activityData);


        activityData.Subject = getPropertyValue(message, "ACT_SUBJECT");
        activityData.Description = getPropertyValue(message, "ACT_BODY");

        try {

            String actTargetDate = getPropertyValue(message, "ACT_TARGET_DATE");
            if(StringUtils.isNotEmpty(actTargetDate)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new SimpleDateFormat(OpportunityCRMMessageToMobileConverter.XML_DATE_FORMAT).parse(actTargetDate));
                activityData.PlannedDate = calendar.getTime();
            }

            //activityData.PlannedDate = DateUtils.parseDate(getPropertyValue(message, "ACT_TARGET_DATE"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        activityData.Type = actTypes.get(getPropertyValue(message, "ENTITY_TYPE"));
        activityData.Status = actStatus.get(getPropertyValue(message, "ACT_STATUS"));

        activityData.Opportunity = new AccountRef();
        activityData.Opportunity.ExternalId =  getPropertyValue(message, "INF_OPPORTUNITY");

        activityData.Contact = new AccountRef();
        activityData.Contact.ExternalId = getPropertyValue(message, "INF_CONTACT");

        activityData.Employee = new AccountRef();
        activityData.Employee.ExternalId = getPropertyValue(message, "SYS_USER");

        return activity;
    }

    @Override
    public String getOrder() {
        return "1";
    }

    @Override
    public String getType() {
        return "Activity";
    }


}
