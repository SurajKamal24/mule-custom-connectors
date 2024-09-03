package com.activemq.internal.operations;

import java.io.IOException;

import javax.jms.JMSException;

import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.parameter.CorrelationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.api.custom.message.CustomJmsMessageAttributes;
import com.activemq.internal.ActiveMQConfiguration;
import com.activemq.internal.connection.CustomConnection;
import com.activemq.internal.custom.jms.CustomConsumer;
import com.activemq.internal.custom.jms.CustomDestination;
import com.activemq.internal.custom.jms.CustomProducer;
import com.activemq.internal.custom.jms.CustomSession;
import com.activemq.internal.custom.jms.CustomTextMessage;
import com.activemq.internal.errors.ActiveMQErrors;
import com.activemq.internal.errors.ConsumeErrorProvider;
import com.activemq.internal.errors.PublishErrorProvider;
import com.activemq.internal.parameters.ConsumeParameters;
import com.activemq.internal.parameters.DestinationParameters;
import com.activemq.internal.parameters.PublishParameters;
import com.activemq.internal.util.CustomResultUtil;



public class ActiveMQOperations {
	
	private static final Logger logger = LoggerFactory.getLogger(ActiveMQOperations.class);
	
	@Alias("publish")
	@Throws(PublishErrorProvider.class)
	public void publishOperation(@ParameterGroup(name = "Destination", showInDsl = true)
								  DestinationParameters destinationParameters,
								  @ParameterGroup(name = "Message", showInDsl = true)
								  PublishParameters publishParameters,
								  @Config ActiveMQConfiguration config,
								  CorrelationInfo correlationInfo,
								  @Connection CustomConnection customConnection) {
		
		logger.info("******PUBLISH operation called");
		CustomSession customSession;
		try {
			customSession = customConnection.createSession();
			CustomDestination customDestination = customSession.createDestination(destinationParameters.getDestinationType(), destinationParameters.getDestination());
			CustomProducer customProducer = customSession.createProducer(customDestination);
			CustomTextMessage customTextMessage = customSession.createCustomTextMessage(publishParameters, correlationInfo);
			customProducer.setPriority(publishParameters.getPriority());
			customProducer.publishMessage(customTextMessage);
			customProducer.close();
			customSession.closeSession();
		} catch (JMSException | IOException e) {
			throw new ModuleException(ActiveMQErrors.PUBLISHING, e);
		}
		
	}
	
	@Alias("consume")
	@Throws(ConsumeErrorProvider.class)
	@MediaType(value = MediaType.ANY, strict = false)
	public Result<String, CustomJmsMessageAttributes> consumeOperation(@ParameterGroup(name = "Destination", showInDsl = true)
									DestinationParameters destinationParameters,
									@ParameterGroup(name = "Basic", showInDsl = true)
									ConsumeParameters consumeParameters,
									@Config ActiveMQConfiguration config,
									@Connection CustomConnection customConnection) {
		
		logger.info("******CONSUME operation called");
		Result<String, CustomJmsMessageAttributes> result = null;
		try {
			CustomSession customSession = customConnection.createSession();
			CustomDestination customDestination = customSession.createDestination(destinationParameters.getDestinationType(), destinationParameters.getDestination());
			CustomConsumer customConsumer = customSession.createConsumer(customDestination, consumeParameters.getSelector());
			CustomTextMessage customTextMessage = customConsumer.consumeMessage(consumeParameters.getMaximumWaitSeconds());
			result = CustomResultUtil.getResult(customTextMessage);
		} catch (JMSException e) {
			throw new ModuleException(ActiveMQErrors.CONSUMING, e);
		}
		
		return result;
	}

}
