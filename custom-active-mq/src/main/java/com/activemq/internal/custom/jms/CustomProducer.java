package com.activemq.internal.custom.jms;

import javax.jms.JMSException;
import javax.jms.MessageProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomProducer {

	private static final Logger logger = LoggerFactory.getLogger(CustomProducer.class);
	
	private final MessageProducer producer;
	
	public CustomProducer(MessageProducer producer) {
		this.producer = producer;
	}
	
	public void close() throws JMSException {
		producer.close();
	}
	
	public void setPriority(int priority) throws JMSException {
		producer.setPriority(priority);
	}
	
	public void publishMessage(CustomTextMessage customTextMessage) throws JMSException {
		logger.info("sending message");
		producer.send(customTextMessage.getTextMessage());
	}

}
