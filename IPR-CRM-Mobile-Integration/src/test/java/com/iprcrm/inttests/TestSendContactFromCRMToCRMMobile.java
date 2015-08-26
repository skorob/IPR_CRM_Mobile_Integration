package com.iprcrm.inttests;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.ipr.crystal.config.EndpointTypeConfiguration;
import com.ipr.crystal.model.enums.DeliveryMode;
import com.ipr.crystal.model.messaging.CrystalMessage;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

public class TestSendContactFromCRMToCRMMobile extends AbstractCrystalJMSTester {

	private static final Logger log = Logger.getLogger(TestSendContactFromCRMToCRMMobile.class);
	
	@Test
	public void test() {
		setTestsCount(1);
		setLastActionTimeOut(10000l);
		generateTests();

		final EndpointTypeConfiguration config = createEndPointTypeConfiguration();

		try {
            for (CrystalMessage message : getGeneratedMessages()) {
                config.send(message, "Crystal!CRM_SYS_IN");
            }

//		config.registerQueueProcessor(new Processor() {
//
//			public void processMessage(CrystalMessage message) {
//				log.info("+++++++++++++ " + message);
//                lastMessage();
//			}
//			}, "Crystal!CRM_TO_MOBILE_INT_SERVICE", 1);
//			this.await();


		} catch (Exception exc) {
			log.error("Error", exc);
		}
		finally {
			config.destroy();
		}
	}

	@Override
	public CrystalMessage generateCrystalMessage(int i) {

		CrystalMessage message = new CrystalMessage();
		message.setQueryVersion("1.0");
		message.setQueryName("PUSH_CONTACT");
		message.setSystemName("CRM");
		try {
			message.setPayload(Resources.toString(Resources.getResource("xml/Contact.xml"), Charsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
		message.setDelivery(DeliveryMode.PERSISTENT);
		message.setIgnoreInProgress(i % 2 == 0);
        message.setFlowId(UUID.randomUUID().toString());
		return message;
	}
}
