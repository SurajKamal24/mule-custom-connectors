package com.activemq.internal.source;

import javax.jms.JMSException;

import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.ConfigOverride;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.runtime.source.Source;
import org.mule.runtime.extension.api.runtime.source.SourceCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.api.custom.message.CustomJmsMessageAttributes;
import com.activemq.internal.ActiveMQConfiguration;
import com.activemq.internal.connection.CustomConnection;
import com.activemq.internal.custom.jms.CustomConsumer;
import com.activemq.internal.custom.jms.CustomDestination;
import com.activemq.internal.custom.jms.CustomSession;
import com.activemq.internal.parameters.DestinationParameters;

@Alias("listener")
@DisplayName("OnNewMessage")
@MediaType(value = MediaType.ANY, strict = false)
public class ActiveMQSource extends Source<String, CustomJmsMessageAttributes>{
	
	private static final Logger logger = LoggerFactory.getLogger(ActiveMQSource.class);
	
	@ParameterGroup(name = "Destination", showInDsl = true)
	private DestinationParameters destinationParameters;
	
	@Connection
	private ConnectionProvider<CustomConnection> connectionProvider;
	
	private CustomConnection customConnection;
	private CustomSession customSession;
	private CustomConsumer customConsumer;
	
	@Parameter
	@Optional
	@ConfigOverride
	private String selector;
	
	@Config
	private ActiveMQConfiguration config;

	@Override
	public void onStart(SourceCallback<String, CustomJmsMessageAttributes> sourceCallback) throws MuleException {
		logger.info("on start called");
		customConnection = connectionProvider.connect();
		try {
			customSession = customConnection.createSession();
			CustomDestination customDestination = customSession.createDestination(destinationParameters.getDestinationType(), destinationParameters.getDestination());
			customConsumer = customSession.createConsumer(customDestination, selector);
			CustomJmsListener customJmsListener = new CustomJmsListener(sourceCallback);
			customConsumer.setMessageListener(customJmsListener);
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onStop() {
		logger.info("on stop called");
		try {
			if(customConnection != null) {
				connectionProvider.disconnect(customConnection);
			}
			if(customConsumer != null) {
				customConsumer.close();
			}
			if(customSession != null) {
				customSession.closeSession();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
