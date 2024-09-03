package com.activemq.internal.custom.jms;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

import org.mule.runtime.extension.api.exception.ModuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.internal.errors.ActiveMQErrors;
import com.activemq.internal.source.CustomJmsListener;

public class CustomConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomConsumer.class);
	
	private final MessageConsumer messageConsumer;
	
	public CustomConsumer(MessageConsumer messageConsumer){
		this.messageConsumer = messageConsumer;
	}
	
	public  CustomTextMessage  consumeMessage(long maxWaitSeconds) throws JMSException {
		javax.jms.Message message  = messageConsumer.receive(maxWaitSeconds);
		logger.info("consume :- received Message" + message);
		if(message == null) {
			throw new ModuleException("No Message Available in destination", ActiveMQErrors.NO_MESSAGE);
		}
		CustomTextMessage customTextMessage = new CustomTextMessage((TextMessage)message);
		return customTextMessage;
	}
	
	public  void setMessageListener(CustomJmsListener jmsListener) throws JMSException, InterruptedException {
		messageConsumer.setMessageListener(jmsListener);
	}

	public void close() throws JMSException {
		messageConsumer.close();
	}
	
}
