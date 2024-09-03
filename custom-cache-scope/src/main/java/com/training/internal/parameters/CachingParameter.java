package com.training.internal.parameters;

import org.mule.runtime.extension.api.annotation.param.Parameter;

public class CachingParameter {
	
	@Parameter
	private String key;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "CachingParameters [key=" + key + "]";
	}

}
