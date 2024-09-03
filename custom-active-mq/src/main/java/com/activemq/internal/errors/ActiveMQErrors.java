package com.activemq.internal.errors;

import java.util.Optional;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

public enum ActiveMQErrors implements ErrorTypeDefinition<ActiveMQErrors>{
	
	PUBLISHING,
	CONSUMING,
	EMPTY_MESSAGE(PUBLISHING),
	NO_MESSAGE(CONSUMING);
	
	private ErrorTypeDefinition<? extends Enum<?>> parent;
	
	ActiveMQErrors() {}
	
	ActiveMQErrors(ErrorTypeDefinition<? extends Enum<?>> parent) {
		this.parent = parent;
	}
	
	public Optional<ErrorTypeDefinition<? extends Enum<?>>> getParent() {
		return Optional.ofNullable(this.parent);
	}

}