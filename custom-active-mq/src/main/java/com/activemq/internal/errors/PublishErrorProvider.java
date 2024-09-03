package com.activemq.internal.errors;

import java.util.HashSet;
import java.util.Set;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

public class PublishErrorProvider implements ErrorTypeProvider {

	@Override
	public Set<ErrorTypeDefinition> getErrorTypes() {
		Set<ErrorTypeDefinition> errorTypes = new HashSet<>();
		errorTypes.add(ActiveMQErrors.PUBLISHING);
		errorTypes.add(ActiveMQErrors.EMPTY_MESSAGE);
		return errorTypes;
	}
	
}
