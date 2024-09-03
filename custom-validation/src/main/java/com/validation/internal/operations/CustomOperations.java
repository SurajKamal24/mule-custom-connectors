package com.validation.internal.operations;

import org.apache.commons.lang3.StringUtils;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.param.stereotype.Validator;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.validation.internal.errors.CustomErrorProvider;
import com.validation.internal.errors.CustomErrors;

public class CustomOperations {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomOperations.class);
	
	@Validator
	@Throws(CustomErrorProvider.class)
	public void isNotBlankString(String value, String errorMessage) {
		if (StringUtils.isBlank(value)) {
			logger.info("value is blank");
			throw new ModuleException(errorMessage, CustomErrors.CUSTOM_BLANK_STRING);		
		}
	}

}
