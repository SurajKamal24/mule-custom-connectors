package com.activemq.internal.parameters;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

public class ConsumerConfigParameters {
	
	@Parameter
	@Optional
	private String selector;
	
	@Parameter
	@Optional(defaultValue = "5")
	@DisplayName("Maximum Wait Seconds")
	private int maximumWaitSeconds;

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		selector = selector;
	}

	public int getMaximumWaitSeconds() {
		return maximumWaitSeconds;
	}

	public void setMaximumWaitSeconds(int maximumWaitSeconds) {
		this.maximumWaitSeconds = maximumWaitSeconds;
	}

}
