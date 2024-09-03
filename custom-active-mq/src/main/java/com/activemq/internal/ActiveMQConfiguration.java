package com.activemq.internal;

import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.Sources;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

import com.activemq.internal.connection.CustomConnectionProvider;
import com.activemq.internal.operations.ActiveMQOperations;
import com.activemq.internal.parameters.ConsumerConfigParameters;
import com.activemq.internal.parameters.ProducerConfigParameters;
import com.activemq.internal.source.ActiveMQSource;

@Configuration
@Operations(ActiveMQOperations.class)
@Sources(ActiveMQSource.class)
@ConnectionProviders(CustomConnectionProvider.class)
public class ActiveMQConfiguration {
	
	@ParameterGroup(name = "ProducerConfig", showInDsl = true)
	@Placement(tab = "producer")
	private ProducerConfigParameters producerConfigParameters;
	
	@ParameterGroup(name = "ConsumerConfig", showInDsl = true)
	@Placement(tab = "consumer")
	private ConsumerConfigParameters consumerConfigParameters;

	
	public ProducerConfigParameters getProducerConfigParameters() {
		return producerConfigParameters;
	}

	public void setProducerConfigParameters(ProducerConfigParameters producerConfigParameters) {
		this.producerConfigParameters = producerConfigParameters;
	}

	public ConsumerConfigParameters getConsumerConfigParameters() {
		return consumerConfigParameters;
	}

	public void setConsumerConfigParameters(ConsumerConfigParameters consumerConfigParameters) {
		this.consumerConfigParameters = consumerConfigParameters;
	}

}
