package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.ipr.iprcrm.integration.integrations.servicebus.dto.Account;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.CRMMobileDataModel;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.Channel;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.Reference;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.ObjectFactory;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by os on 8/25/2015.
 */
public abstract class CRMMobileToCRMMessageConverter <T> {


    public abstract Message convert(T object);

    protected abstract Map<String, String> getChannelsMapping();

    protected void fillData(CRMMobileDataModel cd, ObjectFactory of, Message message) {
        Property extId = of.createProperty();
        extId.setName("CRM_ID");
        extId.setValue(cd.externalId);
        message.getPropertyList().getProperty().add(extId);

        Property order = of.createProperty();
        order.setName("ORDER");
        order.setValue(cd.order);
        message.getPropertyList().getProperty().add(order);

        Property entPk = of.createProperty();
        entPk.setName("SYS_ENTITY_PK");
        entPk.setValue(cd.externalId);
        message.getPropertyList().getProperty().add(entPk);
    }


    protected void assignChannels(CRMMobileDataModel crmMobileDataModel, ObjectFactory of, Message message) {
        for(Channel ch : crmMobileDataModel.channels) {
            Property channel = of.createProperty();
            String channelName = getChannelsMapping().get(ch.Type);
            if(StringUtils.isNotEmpty(channelName)) {
                channel.setValue(ch.Value);
                channel.setName(channelName);
                message.getPropertyList().getProperty().add(channel);
            }
        }
    }

    protected String createRefence(Reference reference) {
        return reference.ExternalId+"|"+reference.Id;
    }
}
