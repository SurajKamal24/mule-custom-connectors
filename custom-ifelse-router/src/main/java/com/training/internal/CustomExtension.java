package com.training.internal;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;

import com.training.internal.operations.CustomOperations;

@Xml(prefix = "CustomIfElse")
@Extension(name = "CustomIfElse")
@Operations(CustomOperations.class)
public class CustomExtension {

}
