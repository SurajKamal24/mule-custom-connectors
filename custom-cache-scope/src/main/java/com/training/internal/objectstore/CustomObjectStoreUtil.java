package com.training.internal.objectstore;

import java.io.IOException;
import java.io.Serializable;

import org.mule.runtime.api.metadata.MediaType;
import org.mule.runtime.api.store.ObjectStoreException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.training.internal.parameters.ValueType;

public class CustomObjectStoreUtil {

	private static final Logger logger = LoggerFactory.getLogger(CustomObjectStoreUtil.class);

	private CustomObjectStoreUtil() {

	}

	public static ValueType createValueType(Result result) throws ObjectStoreException, IOException {
		Serializable attributes;
		Object output = result.getOutput();
		logger.info("result.getOuput() is " + output);
		Serializable payload;
		if (!(output instanceof java.io.Serializable)) {
			throw new IllegalArgumentException("Payload must be serializable");
		} else {
			payload = (Serializable) output;
		}
		java.util.Optional<Object> attrs = result.getAttributes();
		Object attribs = attrs.orElse(null);
		if (!(attribs instanceof java.io.Serializable)) {
			throw new IllegalArgumentException("Attributes must be serializable");
		} else {
			attributes = (Serializable) attribs;
		}
		java.util.Optional<MediaType> mediaType = result.getMediaType();
		MediaType payloadMediaType = mediaType.orElse(MediaType.ANY);
		java.util.Optional<MediaType> attributesMediaType = result.getAttributesMediaType();
		MediaType attrsMediaType = attributesMediaType.orElse(MediaType.ANY);
		ValueType valueType = new ValueType(payload, attributes, payloadMediaType, attrsMediaType);
		logger.info("payload mediaType is " + mediaType);
		logger.info("attrs mediaType is " + attributesMediaType);
		logger.info("***valueType - valueType is " + valueType);
		return valueType;
	}
	
	public static Result getResult(ValueType valueType) throws ObjectStoreException {
		Result result  = 
				Result.
    		    builder()
    	        .output(valueType.getPayload()) 
    	        .mediaType(valueType.getPayloadMediaType())
    	        .attributes(valueType.getAttributes())
    	        .attributesMediaType(valueType.getAttributesMediaType())
    	        .build();
    	return result;
	}

}
