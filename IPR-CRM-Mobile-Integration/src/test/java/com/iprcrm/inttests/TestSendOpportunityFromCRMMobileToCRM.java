package com.iprcrm.inttests;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.ipr.iprcrm.integration.integrations.servicebus.service.AzureConfiguration;
import org.apache.log4j.Logger;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;
import org.junit.Test;

import javax.jms.*;

public class TestSendOpportunityFromCRMMobileToCRM {

	private static final Logger log = Logger.getLogger(TestSendOpportunityFromCRMMobileToCRM.class);
	
	@Test
	public void test() throws Exception {
		AzureConfiguration c = new AzureConfiguration();
		ConnectionFactory cf = c.connectionFactory();
		try {


			Connection connection = cf.createConnection();
			try {
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				try {

					Destination dest = new QueueImpl("out");

					MessageProducer producer = session.createProducer(dest);
					BytesMessage message = session.createBytesMessage();

					message.writeBytes(Resources.toString(Resources.getResource("json/Publish-Lead.json"), Charsets.UTF_8).getBytes());
					System.out.println("Sending message: " + message);
					producer.send(message);
				} finally {
					session.close();
				}
			} finally {
				connection.close();
			}
			Thread.sleep(10000);
		} finally {
			//orderListenerContainer.stop();
			System.out.println("in");
		}
	}


}
