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

public final class CustomUtil {

	private static final Logger logger = LoggerFactory.getLogger(CustomUtil.class);

	private CustomUtil() {

	}

	public static Result<String, CustomJmsMessageAttributes> getResult(CustomTextMessage customTextMessage) throws JMSException {
		TextMessage jmsTextMessage = customTextMessage.getTextMessage();
		Result<String, CustomJmsMessageAttributes> result;
		String payload = null;
		CustomJmsMessageAttributes attributes = new CustomJmsMessageAttributes();
		MediaType payloadMediaType = MediaType.ANY;
		MediaType attributesMediaType = MediaType.APPLICATION_JAVA;
		if (jmsTextMessage != null) {
			payload = jmsTextMessage.getText();
			attributes = constructCustomJmsMessageAttributes(customTextMessage);
			payloadMediaType = constructPayloadMediaType(jmsTextMessage);
		}
		result = constructResult(payload, attributes, payloadMediaType, attributesMediaType);
		return result;
	}

	private static Result<String, CustomJmsMessageAttributes> constructResult(String payload, CustomJmsMessageAttributes attributes, MediaType payloadMediaType, MediaType attributesMediaType) {
		return Result.<String, CustomJmsMessageAttributes>builder().output(payload).mediaType(payloadMediaType)
				.attributes(attributes).attributesMediaType(attributesMediaType).build();
	}

	private static CustomJmsMessageAttributes constructCustomJmsMessageAttributes(CustomTextMessage customTextMessage) throws JMSException {
		CustomJmsMessageHeaders headers = customTextMessage.getHeaders();
		CustomJmsMessageProperties props = customTextMessage.getProperties();
		CustomJmsMessageAttributes attributes = new CustomJmsMessageAttributes(props, headers);
		return attributes;
	}

	private static MediaType constructPayloadMediaType(TextMessage message) throws JMSException {
		MediaType payloadMediaType = MediaType.ANY;
		String mediaType = message.getStringProperty("MM_MESSAGE_CONTENT_TYPE");
		if (mediaType != null) {
			payloadMediaType = createMediaType(mediaType);
		}
		return payloadMediaType;
	}

	private static MediaType createMediaType(String mediaType) throws IllegalArgumentException {
		MediaType mType = MediaType.parse(mediaType);
		return mType;
	}

}