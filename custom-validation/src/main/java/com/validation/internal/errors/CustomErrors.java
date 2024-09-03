package com.validation.internal.errors;

import java.util.Optional;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import org.mule.runtime.extension.api.error.MuleErrors;

public enum CustomErrors implements ErrorTypeDefinition<CustomErrors> {

	CUSTOM_BLANK_STRING(MuleErrors.VALIDATION);

	private ErrorTypeDefinition<? extends Enum<?>> parent;

	CustomErrors(ErrorTypeDefinition<? extends Enum<?>> parent) {
		this.parent = parent;
	}

	@Override
	public Optional<ErrorTypeDefinition<? extends Enum<?>>> getParent() {
		return Optional.ofNullable(this.parent);
	}

}
