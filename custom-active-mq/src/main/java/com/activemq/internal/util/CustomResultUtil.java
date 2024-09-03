package com.activemq.internal.util;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.mule.runtime.api.metadata.MediaType;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.api.custom.message.CustomJmsMessageAttributes;
import com.activemq.api.custom.message.CustomJmsMessageHeaders;
import com.activemq.api.custom.message.CustomJmsMessageProperties;
import com.activemq.internal.custom.jms.CustomTextMessage;

public final class CustomResultUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomResultUtil.class);
	
	private CustomResultUtil() {
		
	} 
	
	public static Result<String,CustomJmsMessageAttributes> getResult(CustomTextMessage customTextMessage) throws JMSException {
		TextMessage jmsTextMessage = customTextMessage.getTextMessage();
		Result<String,CustomJmsMessageAttributes> result = null;
	    if (jmsTextMessage != null) {
	    	String payload = jmsTextMessage.getText();
	    	CustomJmsMessageAttributes attributes = constructAttributes(customTextMessage);
	        MediaType payloadMediaType = getMediaType(jmsTextMessage);
	        MediaType attributesMediaType = MediaType.APPLICATION_JAVA;
	        result =  constructResult(payload,attributes,payloadMediaType,attributesMediaType);    
	     }
	    return result;
	}
	
	private static Result<String, CustomJmsMessageAttributes> constructResult(String payload, CustomJmsMessageAttributes attributes, MediaType payloadMediaType, MediaType attributesMediaType) {
			return Result.
			<String,CustomJmsMessageAttributes>builder() 
			.output(payload)
			.mediaType(payloadMediaType)
			.attributes(attributes) 
			.attributesMediaType(attributesMediaType)
			.build();
	}
	
	public static Result<String, CustomJmsMessageAttributes>  initializeResult(){
		return Result.
				<String,CustomJmsMessageAttributes>builder() 
				.output("Message not yet consumed")
				.build();
	}
	
	private static  CustomJmsMessageAttributes constructAttributes(CustomTextMessage customTextMessage) throws JMSException {
		CustomJmsMessageHeaders headers = customTextMessage.getHeaders();
		CustomJmsMessageProperties props = customTextMessage.getProperties();
	    CustomJmsMessageAttributes  attributes = new CustomJmsMessageAttributes(props,headers);	
	    return attributes;
	}
	
	private static MediaType getMediaType(TextMessage message) throws JMSException {
		MediaType payloadMediaType = MediaType.ANY;
		String mediaType = message.getStringProperty("MM_MESSAGE_CONTENT_TYPE");		     		
        if(mediaType != null) {
        	payloadMediaType = createMediaType(mediaType);
        }
        return payloadMediaType;
	}
	    
	private static MediaType createMediaType(String mediaType) throws IllegalArgumentException {
		MediaType mType = MediaType.parse(mediaType);      
	    return mType;
	}
	
}