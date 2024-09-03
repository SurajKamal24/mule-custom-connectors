package com.activemq.internal.parameters;

import java.io.InputStream;
import java.util.Map;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.ConfigOverride;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.NullSafe;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class PublishParameters {
	
	@Content(primary = true)
	@Parameter
	@Summary("The body of the message")
	//Expression is supported by default
	private TypedValue<InputStream> body;
	
	@Parameter
	@Optional
	@Expression(ExpressionSupport.NOT_SUPPORTED)
	@Example("application/json")
	@DisplayName("Content-Type")
	@Summary("Content Type of message")
	private String contentType;
	
	@Parameter
	@Optional
	@Expression(ExpressionSupport.NOT_SUPPORTED)
	@Example("UTF-8")
	@Summary("Encoding of message")
	private String encoding;
	
	@Parameter
	@Optional
	@DisplayName("JMS Type")
	@Summary("The JmsType identifier header")
	@ConfigOverride
	private String type;
	
	@Parameter
	@Optional
	@Summary("The message priority header")
	@ConfigOverride
	private int priority;
	
	@Parameter
	@Optional
	@Example(value = "#[uuid()]")
	@Summary("The Correlation Id header")
	private String correlationId;
	
	@NullSafe
	@Content
	@Parameter
	@Optional
	@DisplayName("User Properties")
	@Summary("The custom user properties that can be set to this message")
	private Map<String, String> userProperties;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public Map<String, String> getUserProperties() {
		return userProperties;
	}

	public void setUserProperties(Map<String, String> userProperties) {
		this.userProperties = userProperties;
	}

	public void setBody(TypedValue<InputStream> body) {
		this.body = body;
	}

	public TypedValue<InputStream> getBody() {
		return body;
	}
	
	

}
