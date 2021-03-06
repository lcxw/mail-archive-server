package org.apache.sling.mailarchiveserver.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.sling.mailarchiveserver.api.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ConnectorScheduler implements Runnable {

	private static final int SLEEP_TIME_BETWEEN_NEW_MAIL_CHECKS = 20;

	private static final Logger logger = LoggerFactory.getLogger(ConnectorScheduler.class);
	private static final int WAITING_TIME_LIMIT_BEFORE_TERMINATION = 5000; 

	private boolean running = true;
	private Thread executionThread = null;

	@Reference(
			cardinality=ReferenceCardinality.OPTIONAL_MULTIPLE, 
			policy=ReferencePolicy.DYNAMIC,
			referenceInterface=Connector.class)
	private List<Connector> scheduledConnectors = new ArrayList<Connector>();
	private Deque<Connector> executionQueue;

	@Activate
	public void activate() {
		executionThread = new Thread(this, "Connector Scheduler's execution thread");
		executionThread.setDaemon(true);
		executionThread.start(); 
	}

	@Deactivate
	public void deactivate() throws InterruptedException {
		if (executionThread != null) {
			running = false;
			executionThread.join(WAITING_TIME_LIMIT_BEFORE_TERMINATION);
		}
	}

	@Override
	public void run() {
		while (running) {
			executionQueue = new ArrayDeque<Connector>(scheduledConnectors);
			while (!executionQueue.isEmpty() && running) {
				Connector c = executionQueue.remove();
				int retreived = c.checkNewMessages();
				logger.info("Processed {} messages using \"{}\" connector.", retreived, c.toString());
			}
			try {
				TimeUnit.SECONDS.sleep(SLEEP_TIME_BETWEEN_NEW_MAIL_CHECKS);
			} catch (InterruptedException e) {
				running = false;
			}
		}
	}

	public synchronized void bindConnector(Connector c) {
		logger.info("Connector " + c.toString() + " added to pool.");
		scheduledConnectors.add(c);
	}

	public synchronized void unbindConnector(Connector c) {
		logger.info("Connector " + c.toString() + " removed from pool.");
		scheduledConnectors.remove(c);
	}

}
