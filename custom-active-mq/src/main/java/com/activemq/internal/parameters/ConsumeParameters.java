package com.activemq.internal.parameters;

import org.mule.runtime.extension.api.annotation.param.ConfigOverride;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

public class ConsumeParameters {
	
	@Parameter
	@Optional
	@ConfigOverride
	private String selector;
	
	@Parameter
	@Optional	
	@ConfigOverride
	private int maximumWaitSeconds;

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public int getMaximumWaitSeconds() {
		return maximumWaitSeconds;
	}

	public void setMaximumWaitSeconds(int maximumWaitSeconds) {
		this.maximumWaitSeconds = maximumWaitSeconds;
	}

}
