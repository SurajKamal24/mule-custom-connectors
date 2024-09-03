package com.training.internal.parameters;

import java.io.Serializable;

import org.mule.runtime.api.metadata.MediaType;

public class ValueType implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Serializable payload;
	private Serializable attributes;
	
	private MediaType payloadMediaType;
	private MediaType attributesMediaType;
	
	public ValueType() {
		super();
	}
	
	public ValueType(Serializable payload, Serializable attributes, MediaType payloadMediaType,
			MediaType attributesMediaType) {
		super();
		this.payload = payload;
		this.attributes = attributes;
		this.payloadMediaType = payloadMediaType;
		this.attributesMediaType = attributesMediaType;
	}

	public Serializable getPayload() {
		return payload;
	}

	public Serializable getAttributes() {
		return attributes;
	}

	public MediaType getPayloadMediaType() {
		return payloadMediaType;
	}

	public MediaType getAttributesMediaType() {
		return attributesMediaType;
	}

	@Override
	public String toString() {
		return "ValueType [payload=" + payload + ", attributes=" + attributes + ", payloadMediaType=" + payloadMediaType
				+ ", attributesMediaType=" + attributesMediaType + "]";
	}

}
