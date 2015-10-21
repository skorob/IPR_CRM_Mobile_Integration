package com.iprcrm.inttests.mobile;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.ipr.iprcrm.integration.Application;
import com.ipr.iprcrm.integration.integrations.servicebus.service.AzureConfiguration;
import org.apache.log4j.Logger;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TestSendLeadFromCRMMobileToCRM {

	private static final Logger log = Logger.getLogger(TestSendLeadFromCRMMobileToCRM.class);

	@Autowired
	AzureConfiguration c;
	@Test
	public void test() throws Exception {

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
