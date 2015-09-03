package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by os on 8/25/2015.
 */
public abstract class CRMMessageToMobileConverter <T extends CRMMobileModel> {

    public abstract T convert(Message message);

    public abstract String getOrder();
    public abstract String getType();

    protected abstract Map<String, String> getChannelsMapping();


    public void init(CRMMobileModel crmMobileModel, Message message, CRMMobileDataModel crmMobileDataModel) {
        crmMobileModel.header = new Header();
        crmMobileModel.header.Id = message.getCrystalCorrId();
        crmMobileModel.header.Source = "iprcrm";
        crmMobileModel.header.Timestamp = new Date();

        crmMobileDataModel.order = getOrder();
        crmMobileDataModel.type =  getType();
        crmMobileDataModel.Id =  getPropertyValue(message, "CORRELATION_ID");
        crmMobileDataModel.externalId = getPropertyValue(message, "CRM_ID");
    }

    protected String getPropertyValue(Message message, String propName) {
        List<Property> props = message.getPropertyList().getProperty();
        for(Property p : props) {
            if(p.getName().equals(propName)) {
                return  p.getValue();
            }
        }
        return null;
    }

    protected void assignChannel(Message message, String channelType, CRMMobileDataModel accountData) {
        String value = getPropertyValue(message, channelType);
        if(StringUtils.isNotEmpty(value)) {

            String mobileChannelType = getChannelsMapping().get(channelType);
            if(StringUtils.isNotEmpty(mobileChannelType)) {
                if(accountData.channels == null) {
                    accountData.channels = new ArrayList<>();
                }
                Channel channel = new Channel();
                channel.Type = mobileChannelType;
                channel.Value = value;
                accountData.channels.add(channel);
            }

        }
    }

    protected Reference parseIdToRef(String value) {
        Reference ref = null;
        if(StringUtils.isNotEmpty(value)) {
            ref =  new Reference();
            String [] ids = value.split("\\|");
            if(ids.length>0) {
                ref.ExternalId = ids[0];
            }

            if(ids.length > 1) {
                ref.Id = ids[1];
            }
        }


        return  ref;
    }
}
