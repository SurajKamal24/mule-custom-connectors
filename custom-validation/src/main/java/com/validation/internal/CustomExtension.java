package com.validation.internal;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;

import com.validation.internal.errors.CustomErrors;
import com.validation.internal.operations.CustomOperations;

@Xml(prefix = "custom-validation")
@Extension(name = "CustomValidation")
@Operations(CustomOperations.class)
@ErrorTypes(CustomErrors.class)
public class CustomExtension {

}
