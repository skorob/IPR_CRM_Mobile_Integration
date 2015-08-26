package com.iprcrm.inttests;

import com.ipr.crystal.commons.builders.EndpointTypesBuilder;
import com.ipr.crystal.config.EndpointTypeConfiguration;
import com.ipr.crystal.model.EndpointTypeEntity;
import com.ipr.crystal.model.messaging.CrystalMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractCrystalJMSTester {

	private static EndpointTypeEntity endpointTypeEntity;
	private int testsCount;
	private Map<String,CrystalMessage> generatedMessages;
	private volatile long lastMessageTime;
	private CountDownLatch lock = new CountDownLatch(1);
	private AtomicInteger responsesCount;
	private Long lastActionTimeOut = 40000l;
	private List<String> issues = new ArrayList<String>();


	public void await() {
		startCountDown();
		try {
			this.lock.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Long getLastActionTimeOut() {
		return this.lastActionTimeOut;
	}

	public void setLastActionTimeOut(Long lastActionTimeOut) {
		this.lastActionTimeOut = lastActionTimeOut;
	}


	public void incResponseCount() {
		this.responsesCount.incrementAndGet();
	}

	public int getResponsesCount() {
		return this.responsesCount.intValue();
	}

	public int getTestsCount() {
		return this.testsCount;
	}

	public void setTestsCount(int testsCount) {
		this.testsCount = testsCount;
	}

	public Collection<CrystalMessage> getGeneratedMessages() {
		return this.generatedMessages.values();
	}

	public void init() {
		this.responsesCount = new AtomicInteger(0);
		if (endpointTypeEntity == null) {
			endpointTypeEntity = new EndpointTypeEntity();
			endpointTypeEntity.setFactory("ConnectionFactory");
			endpointTypeEntity.setFactoryInitial("org.hornetq.jms.client.HornetQJMSConnectionFactory");
			endpointTypeEntity.setProviderUrl("http://192.168.2.223:5445");
			endpointTypeEntity.setSecurityCredentials("guest");
			endpointTypeEntity.setSecurityPrincipal("guest");

		}
	}

	public void startCountDown() {
		lastMessage();
		(new Thread() {
			@Override
			public synchronized void run() {
				while (System.currentTimeMillis() - AbstractCrystalJMSTester.this.lastMessageTime < (AbstractCrystalJMSTester.this.lastActionTimeOut == -1 ? Long.MAX_VALUE
						: AbstractCrystalJMSTester.this.lastActionTimeOut)) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
				}
				AbstractCrystalJMSTester.this.lock.countDown();
			}
		}).start();
	}

	public EndpointTypeConfiguration createEndPointTypeConfiguration() {
		init();
		return EndpointTypesBuilder
				.buildEndPointTypeConfiguration(endpointTypeEntity);
	}

	public abstract CrystalMessage generateCrystalMessage(int i);

	public void generateTests() {
		this.lastMessageTime = Long.MAX_VALUE;
		this.generatedMessages = new ConcurrentHashMap<String, CrystalMessage>(getTestsCount());
		for (int i = 0; i < getTestsCount(); i++) {
			if (i % 500 == 0) {
				System.out.println(i);
			}
			CrystalMessage crystalMessage = generateCrystalMessage(i);
			this.generatedMessages.put(crystalMessage.getFlowId(), crystalMessage);
		}
	}

	public void registerResponse(CrystalMessage message) {
		this.incResponseCount();
		if(StringUtils.isNotEmpty(message.getFlowId())) {
			this.generatedMessages.remove(message.getFlowId());
		}
	}

	public void lastMessage() {
		this.lastMessageTime = System.currentTimeMillis();
	}

	public void registerIssue(String issue) {
		this.issues.add(issue);
	}

	public int getIssuesCount() {
		return this.issues.size();
	}

}
