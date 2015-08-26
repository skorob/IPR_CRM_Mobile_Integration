package com.ipr.iprcrm.integration.integrations.servicebus.converters;

import com.ipr.iprcrm.integration.integrations.servicebus.dto.Account;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;

/**
 * Created by os on 8/25/2015.
 */
public abstract class CRMMobileToCRMMessageConverter <T> {

    public abstract Message convert(T object);
}
