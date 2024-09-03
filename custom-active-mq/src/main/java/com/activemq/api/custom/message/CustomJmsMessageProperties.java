package com.activemq.api.custom.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.mule.runtime.extension.api.annotation.param.Parameter;

public class CustomJmsMessageProperties implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Parameter
	private  Map<String, String> userProperties;
	
	public CustomJmsMessageProperties() {
		super();
	}


	public CustomJmsMessageProperties(Map<String, String> userProperties) {
		this.userProperties = userProperties;	 
	}
	
	public CustomJmsMessageProperties(Map<String, String> userProperties, String contentType,String encoding) {
		userProperties.put("MM_MESSAGE_CONTENT_TYPE", contentType);
		userProperties.put("MM_MESSAGE_ENCODING", encoding);
		this.userProperties = userProperties;
	}
	
	public Map<String, String> getUserProperties() {
		HashMap<String, String> newUserProperties = SerializationUtils.clone(new HashMap<>(this.userProperties));
		return newUserProperties;
	}
	
	@Override
	public String toString() {
		return "userProperties=" + userProperties ;
	}
	
}
