package com.activemq.internal.connection;

import javax.jms.JMSException;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.meta.ExternalLibraryType;
import org.mule.runtime.extension.api.annotation.ExternalLib;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.internal.custom.jms.CustomProducer;
import com.activemq.internal.parameters.ConnectionParameters;

@ExternalLib(
name = "ActiveMQ Client",
description = "The ActiveMQ JMS client implementation",
type = ExternalLibraryType.DEPENDENCY,
requiredClassName = "org.apache.activemq.ActiveMQConnectionFactory",
coordinates = "org.apache.activemq:activemq-client:5.15.3"
)
public class CustomConnectionProvider implements ConnectionProvider<CustomConnection> {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomProducer.class);
	
	@ParameterGroup(name = "ConnectionDetails", showInDsl = true)
	private ConnectionParameters connectionParameters;

	@Override
	public CustomConnection connect() throws ConnectionException {
		CustomConnection customConnection = null;
		try {
			customConnection = new CustomConnection(connectionParameters.getUsername(), connectionParameters.getPassword(), connectionParameters.getBrokerUrl());
		} catch (JMSException e) {
			throw new ConnectionException("Error occurred trying to connect.", e);
		}
		return customConnection;
	}

	@Override
	public void disconnect(CustomConnection customConnection) {
		try {
			customConnection.invalidateConnection();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ConnectionValidationResult validate(CustomConnection customConnection) {
		boolean isValid = customConnection.isValid();
		ConnectionValidationResult result;
		if (isValid) {
			result = ConnectionValidationResult.success();
		} else {
			result = ConnectionValidationResult.failure("Connection is invalid", new RuntimeException("Invalid Connection"));
		}
		return result;
	}

}
