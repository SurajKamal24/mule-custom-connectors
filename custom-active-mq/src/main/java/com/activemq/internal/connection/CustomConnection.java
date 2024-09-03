package com.activemq.internal.connection;



import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.internal.custom.jms.CustomProducer;
import com.activemq.internal.custom.jms.CustomSession;

public class CustomConnection {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomProducer.class);
	private Connection connection;
	
	public CustomConnection(String userName, String password, String brokerUrl) throws JMSException {
		logger.info("Creating ActiveMQ Connection factory");
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerUrl);
		logger.info("Creating connection");
		this.connection = connectionFactory.createConnection();
		connection.start();
	}
	
	public void invalidateConnection() throws JMSException {
		logger.info("Invalidation connection");
		if (connection != null) {
			connection.close();
		}
	}
	
	public boolean isValid() {
		logger.info("Validity checking of connection");
		boolean isValid = true;
		Session session;
		try {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			TextMessage msg = session.createTextMessage("connection test");
			session.close();
		} catch (JMSException e) {
			logger.info("JMS connection is invalid");
			isValid = false;
			e.printStackTrace();
		}
		
		return isValid;
	}
	
	public Connection getConnection() {
		logger.info("CustomConnection Class : getConnection method");
		return connection;
	}
	
	public CustomSession createSession() throws JMSException {
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		return new CustomSession(session);	
	}

}
