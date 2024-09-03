package com.activemq.internal.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Optional;

import javax.jms.JMSException;

import org.apache.commons.io.IOUtils;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.MediaType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.extension.api.runtime.parameter.CorrelationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomJmsUtil {

	private static final Logger logger = LoggerFactory.getLogger(CustomJmsUtil.class);

	private CustomJmsUtil() {

	}

	public static String getBody(TypedValue<InputStream> typedValueBody) throws IOException {
		InputStream theContent = typedValueBody.getValue();
		StringWriter writer = new StringWriter();
		IOUtils.copy(theContent, writer);
		String messageBody = writer.toString();
		return messageBody;
	}

	public static String resolveContentType(TypedValue<InputStream> messageBody, String userContentType) {
		String contentType = null;
		if (userContentType != null) {
			contentType = userContentType;
		} else {
			DataType dataType = messageBody.getDataType();
			MediaType mediaType = dataType.getMediaType();
			contentType = mediaType.getPrimaryType() + "/" + mediaType.getSubType();
			logger.info("setting message content type");
		}
		return contentType;
	}

	public static String resolveEncoding(TypedValue<InputStream> messageBody, String userEncoding) {
		String encoding = null;
		if (userEncoding != null) {
			encoding = userEncoding;
			logger.info("setting user set publish operation encoding");
		} else {
			DataType dataType = messageBody.getDataType();
			MediaType mediaType = dataType.getMediaType();
			Optional<Charset> charSet = mediaType.getCharset();
			if (charSet.isPresent())
				encoding = charSet.get().toString();
			else
				encoding = "UTF-8";
			logger.info("setting message encoding");
		}
		return encoding;
	}

	public static String resolveCorrelationId(String userSetCorrelationId, CorrelationInfo correlationInfo) throws JMSException {
		String correlationId = null;
		if (userSetCorrelationId != null) {
			correlationId = userSetCorrelationId;
			logger.info("setting user set correlationId ");
		} else {
			correlationId = correlationInfo.getCorrelationId();
			logger.info("using already existing correlationId ");
		}
		return correlationId;
	}

}
