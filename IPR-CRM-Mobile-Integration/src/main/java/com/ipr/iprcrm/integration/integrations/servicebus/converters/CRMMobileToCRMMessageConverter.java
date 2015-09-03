package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.ipr.iprcrm.integration.integrations.servicebus.dto.*;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.ObjectFactory;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by os on 8/25/2015.
 */
public abstract class CRMMobileToCRMMessageConverter <T extends  CRMMobileModel, E extends  CRMMobileDataModel> {


    public Message convert(T object) {

            E crmMobileDataModel = (E)object.body.data.get(0);

            ObjectFactory of = new ObjectFactory();

            Message message = convertToMessage(crmMobileDataModel);

            fillHeaders(object, of, message);

            return message;

    }

    protected abstract Map<String, String> getChannelsMapping();

    public abstract Message convertToMessage(E opportunityData);

    protected void fillCommonData(CRMMobileDataModel cd, ObjectFactory of, Message message) {
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



    protected void fillHeaders(CRMMobileModel crmMobileModel, ObjectFactory of, Message message) {
        message.setCrystalCorrId(crmMobileModel.header.Id);

        Property corrId = of.createProperty();
        corrId.setName("CORRELATION_ID");
        corrId.setValue(crmMobileModel.header.Id);
        message.getPropertyList().getProperty().add(corrId);
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
