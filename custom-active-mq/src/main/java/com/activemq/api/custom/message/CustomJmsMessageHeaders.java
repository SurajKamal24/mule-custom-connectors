package com.activemq.api.custom.message;

import java.io.Serializable;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomJmsMessageHeaders implements Serializable {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomJmsMessageHeaders.class);
	
	private static final long serialVersionUID = 1L;
	
	@Parameter
	@Optional
	private String type;
	
	@Parameter
	@Optional
	private Integer priority;
	
	@Parameter
	@Optional
	private String correlationId;
	
	public CustomJmsMessageHeaders() {
		super();
	}
	
	public CustomJmsMessageHeaders(String type, Integer priority, String correlationId) {
		super();
		this.type = type;
		this.priority = priority;
		this.correlationId = correlationId;	
	}
	
	public String getType() {
		return type;
	}

	public Integer getPriority() {
		return priority;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	@Override
	public String toString() {
		return "JmsMessageHeaders [type=" + type + ", priority=" + priority + ", correlationId=" + correlationId + "]";
	}
	
}
