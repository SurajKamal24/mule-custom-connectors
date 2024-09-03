package com.activemq.internal.custom.jms;

import java.io.IOException;
import java.io.InputStream;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.parameter.CorrelationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.api.custom.message.CustomJmsMessageHeaders;
import com.activemq.api.custom.message.CustomJmsMessageProperties;
import com.activemq.internal.errors.ActiveMQErrors;
import com.activemq.internal.parameters.PublishParameters;
import com.activemq.internal.util.CustomJmsUtil;

public class CustomSession {
	private static final Logger logger = LoggerFactory.getLogger(CustomProducer.class);

	private final Session session;

	public CustomSession(Session session) {
		this.session = session;
	}

	public CustomDestination createDestination(String destinationType, String destinationName) throws JMSException {
		Destination dest = null;
		if ("QUEUE".equals(destinationType)) {
			dest = session.createQueue(destinationName);
		} else {
			dest = session.createTopic(destinationName);
		}
		CustomDestination customDestination = new CustomDestination(dest);
		return customDestination;
	}

	public CustomProducer createProducer(CustomDestination customDestination) throws JMSException {
		MessageProducer producer = session.createProducer(customDestination.getDestination());
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		CustomProducer customJmsProducer = new CustomProducer(producer);
		return customJmsProducer;
	}

	public CustomConsumer createConsumer(CustomDestination customJmsDestination, String selector) throws JMSException {
		Destination destination = customJmsDestination.getDestination();
		logger.info("Selector " + selector);
		MessageConsumer consumer = session.createConsumer(destination, selector);
		return new CustomConsumer(consumer);

	}

	public CustomTextMessage createCustomTextMessage(PublishParameters publishParams, CorrelationInfo correlationInfo)
			throws IOException, JMSException {
		TypedValue<InputStream> typedValueBody = publishParams.getBody();
		String jmsMessageBody = CustomJmsUtil.getBody(typedValueBody);
		CustomTextMessage customTextMessage = null;
		if (StringUtils.isBlank(jmsMessageBody)) {
			logger.info("messageBody IS Empty");
			throw new ModuleException("You are trying to POST empty message", ActiveMQErrors.EMPTY_MESSAGE);

		} else {
			String contentType = CustomJmsUtil.resolveContentType(typedValueBody, publishParams.getContentType());
			String encoding = CustomJmsUtil.resolveEncoding(typedValueBody, publishParams.getEncoding());
			String correlationId = CustomJmsUtil.resolveCorrelationId(publishParams.getCorrelationId(), correlationInfo);
			CustomJmsMessageProperties customJmsMessageProperties = new CustomJmsMessageProperties(
					publishParams.getUserProperties(), contentType, encoding);
			CustomJmsMessageHeaders customJmsMessageHeaders = new CustomJmsMessageHeaders(publishParams.getType(),
					publishParams.getPriority(), correlationId);
			TextMessage txtMessage = session.createTextMessage(jmsMessageBody);
			customTextMessage = new CustomTextMessage(txtMessage);
			customTextMessage.setHeaders(customJmsMessageHeaders);
			customTextMessage.setProperties(customJmsMessageProperties);
		}
		return customTextMessage;
	}

	public void closeSession() throws JMSException {
		session.close();
	}

	public Session getSession() {
		return session;
	}
}
