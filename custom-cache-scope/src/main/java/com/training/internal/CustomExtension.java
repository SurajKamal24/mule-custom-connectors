package com.training.internal;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;

import com.training.internal.operations.CustomOperations;

@Xml(prefix = "CustomCacheScope")
@Extension(name = "CustomCacheScope")
@Operations(CustomOperations.class)
public class CustomExtension {

}
