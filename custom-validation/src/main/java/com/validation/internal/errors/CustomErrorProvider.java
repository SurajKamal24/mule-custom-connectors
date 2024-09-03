package com.validation.internal.errors;

import java.util.HashSet;
import java.util.Set;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

public class CustomErrorProvider implements ErrorTypeProvider {

	@Override
	public Set<ErrorTypeDefinition> getErrorTypes() {
		Set<ErrorTypeDefinition> errors = new HashSet<>();
		errors.add(CustomErrors.CUSTOM_BLANK_STRING);
		return errors;
	}

}
