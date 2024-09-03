package com.activemq.internal;

import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;

import com.activemq.internal.errors.ActiveMQErrors;

@Extension(name = "ActiveMQ")
@Xml(prefix = "customactivemq")
@Configurations(ActiveMQConfiguration.class)
@ErrorTypes(ActiveMQErrors.class)
public class ActiveMQExtension {

}
