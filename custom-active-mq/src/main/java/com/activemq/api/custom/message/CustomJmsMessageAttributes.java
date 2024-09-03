package com.activemq.api.custom.message;

import java.io.Serializable;

import org.mule.runtime.extension.api.annotation.param.Parameter;

public class CustomJmsMessageAttributes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Parameter
	private  CustomJmsMessageProperties properties;
	
	@Parameter
	private  CustomJmsMessageHeaders headers;
	
	public CustomJmsMessageAttributes() {
		super();
	}
	
	public CustomJmsMessageAttributes(CustomJmsMessageProperties customJmsMessageProperties, CustomJmsMessageHeaders customJmsMessageHeaders) {
		this.properties = customJmsMessageProperties;
		this.headers = customJmsMessageHeaders;
	}
	
	public CustomJmsMessageProperties getProperties() {
		return properties;
	}
	
	public void setProperties(CustomJmsMessageProperties properties) {
		this.properties = properties;
	}
	
	public CustomJmsMessageHeaders getHeaders() {
		return headers;
	}
	
	public void setHeaders(CustomJmsMessageHeaders headers) {
		this.headers = headers;
	}
	
	@Override
	public String toString() {
		return "CustomJmsMessageAttributes [properties=" + properties + ", headers=" + headers + "]";
	}

}
