package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by os on 8/21/2015.
 */
@Component
public class ActivityCRMMessageToMobileConverter extends CRMMessageToMobileConverter<Activity> {



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
        String entityType = getPropertyValue(message, "ENTITY_TYPE");
        activityData.Type = Mappings.getActivityTypeMappingCRMToCRMMobile().get(entityType);

        if(StringUtils.isNotEmpty(entityType)) {
            if(entityType.equals("INF_EMAIL_OUT")) {
                activityData.Status = Mappings.getActivityMailStatusMappingCRMToCRMMobile().get(getPropertyValue(message, "ACT_STATUS"));
            } else {

                activityData.Status = Mappings.getActivityStatusMappingCRMToCRMMobile().get(getPropertyValue(message, "ACT_STATUS"));
            }

        }

        activityData.Opportunity =  parseIdToRef(getPropertyValue(message, "INF_OPPORTUNITY"));

        activityData.Contact = parseIdToRef(getPropertyValue(message, "INF_CONTACT"));

        activityData.Employee = parseIdToRef(getPropertyValue(message, "SYS_USER"));

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

    @Override
    protected Map<String, String> getChannelsMapping() {
        throw new UnsupportedOperationException();
    }


}
