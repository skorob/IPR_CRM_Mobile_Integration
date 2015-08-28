package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.ipr.iprcrm.integration.integrations.servicebus.dto.Account;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.CRMMobileDataModel;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import com.ipr.pa.policyclient.ws.crystal.schemas.ObjectFactory;
import com.ipr.pa.policyclient.ws.crystal.schemas.Property;

/**
 * Created by os on 8/25/2015.
 */
public abstract class CRMMobileToCRMMessageConverter <T> {

    public abstract Message convert(T object);

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
}
