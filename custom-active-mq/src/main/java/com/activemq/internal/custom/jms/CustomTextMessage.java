package com.activemq.internal.custom.jms;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.api.custom.message.CustomJmsMessageHeaders;
import com.activemq.api.custom.message.CustomJmsMessageProperties;

public class CustomTextMessage {

private TextMessage textMessage;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomTextMessage.class);
	
	public CustomTextMessage(TextMessage textMessage) {
		this.textMessage = textMessage;
	}

	public TextMessage getTextMessage() {
		return textMessage;
	}
	
	public  void setHeaders(CustomJmsMessageHeaders customJmsMessageHeaders) throws JMSException {
		textMessage.setJMSType(customJmsMessageHeaders.getType());
		textMessage.setJMSCorrelationID(customJmsMessageHeaders.getCorrelationId());
		textMessage.setJMSPriority(customJmsMessageHeaders.getPriority());
		logger.info("custom text message " + customJmsMessageHeaders.getPriority());
	}
	
	public void setProperties(CustomJmsMessageProperties customJmsMessageProperties) throws JMSException {
		Map<String,String> userProperties = customJmsMessageProperties.getUserProperties();
		if(userProperties != null) {
			for (Map.Entry<String,String> entry : userProperties.entrySet()) {
				textMessage.setStringProperty(entry.getKey(), entry.getValue());
			}
		}
	}
	
	public CustomJmsMessageHeaders getHeaders() throws JMSException {
		int priority = textMessage.getJMSPriority();	
	    String type = textMessage.getJMSType();	
		String correlationId = textMessage.getJMSCorrelationID();
		CustomJmsMessageHeaders customJmsMessageHeaders = new CustomJmsMessageHeaders(type,priority,correlationId);
        return customJmsMessageHeaders;
    }
	
	public CustomJmsMessageProperties getProperties() throws JMSException {
		Enumeration propertyNames = textMessage.getPropertyNames();
		HashMap<String,String> userProperties = new HashMap<>();
    	while (propertyNames.hasMoreElements()) {
            String propertyName = (String)propertyNames.nextElement();
            userProperties.put(propertyName, textMessage.getStringProperty(propertyName));
    	}
        CustomJmsMessageProperties customJmsMessageProperties  = new CustomJmsMessageProperties(userProperties);
        return customJmsMessageProperties;
	}
	
	public String getCustomText() throws JMSException {
    	return this.textMessage.getText();
    }

	public void setTextMessage(TextMessage textMessage) {
		this.textMessage = textMessage;
	}
}
