package com.activemq.internal.errors;

import java.util.HashSet;
import java.util.Set;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

public class ConsumeErrorProvider implements ErrorTypeProvider {

	@Override
	public Set<ErrorTypeDefinition> getErrorTypes() {
		Set<ErrorTypeDefinition> errorTypes = new HashSet<>();
		errorTypes.add(ActiveMQErrors.CONSUMING);
		errorTypes.add(ActiveMQErrors.NO_MESSAGE);
		return errorTypes;
	}
	
}
