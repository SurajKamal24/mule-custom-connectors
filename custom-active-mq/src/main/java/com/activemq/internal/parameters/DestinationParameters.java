package com.activemq.internal.parameters;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.annotation.values.OfValues;

public class DestinationParameters {
	
	@Parameter
	@Summary("The name of the destination")
	private String destination;

	@Parameter
	@Summary("The type of the destination")
	@Optional(defaultValue = "QUEUE")
	@OfValues(DestinationTypeProvider.class)
	private String destinationType;

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}
	
}
