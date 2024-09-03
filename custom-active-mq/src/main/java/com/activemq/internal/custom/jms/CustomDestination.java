package com.activemq.internal.custom.jms;

import javax.jms.Destination;

public class CustomDestination {
	
	private final Destination destination;
	
	public CustomDestination(Destination destination) {
		this.destination = destination;
	}
	
	public Destination getDestination() {
		return destination;
	}

}
