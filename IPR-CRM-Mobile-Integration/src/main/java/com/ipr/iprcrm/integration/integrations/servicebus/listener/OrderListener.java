package com.ipr.iprcrm.integration.integrations.servicebus.listener;

import com.ipr.iprcrm.integration.integrations.servicebus.converters.AccountCRMMobileToCRMMessageConverter;
import com.ipr.iprcrm.integration.integrations.servicebus.converters.JsonToCRMMobileModelConverter;
import com.ipr.iprcrm.integration.integrations.servicebus.dto.Account;
import com.ipr.iprcrm.integration.integrations.servicebus.service.CRMService;
import com.ipr.pa.policyclient.ws.crystal.schemas.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.qpid.amqp_1_0.jms.BytesMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MessageListener;

@Component
public class OrderListener implements MessageListener {


    @Autowired
    JsonToCRMMobileModelConverter jsonToCRMMobileModelConverter;

    @Autowired
    AccountCRMMobileToCRMMessageConverter accountCRMMobileToCRMMessageConverter;

    Log log = LogFactory.getLog(OrderListener.class);

    @Autowired
    CRMService crmService;

    public void onMessage(javax.jms.Message message) {
        String json = null;
        try {
            BytesMessage bytesMessage = (BytesMessage)message;
            byte[] bytes  = new byte[(int)bytesMessage.getBodyLength()];
            bytesMessage.readBytes(bytes);
            String jsonS = new String(bytes,"UTF-8");
            log.info("Azure OUT queue. The message is received  ["+jsonS +"]");
            Account account = jsonToCRMMobileModelConverter.convert(jsonS);
            Message crmMessage = accountCRMMobileToCRMMessageConverter.convert(account);
            crmService.sendAccountMessage(crmMessage);

            message.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

}
