package com.activemq.internal.source;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.source.SourceCallback;
import org.mule.runtime.extension.api.runtime.source.SourceCallbackContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.api.custom.message.CustomJmsMessageAttributes;
import com.activemq.internal.custom.jms.CustomTextMessage;
import com.activemq.internal.util.CustomResultUtil;



public class CustomJmsListener implements javax.jms.MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(CustomJmsListener.class);
	private SourceCallback<String, CustomJmsMessageAttributes> sourceCallback;

	public CustomJmsListener(SourceCallback<String, CustomJmsMessageAttributes> sourceCallback) {
		this.sourceCallback = sourceCallback;
		logger.info("CustomJmsListener Created - constructor called");
	}
	
	@Override
	public void onMessage(Message jmsMessage) {
		logger.info("OnNewMessage - on message called " );
		if (jmsMessage instanceof TextMessage) {
			TextMessage jmsTextMessage = (TextMessage) jmsMessage;
			try {	
				CustomTextMessage customTextMessage = new CustomTextMessage(jmsTextMessage);
				String correlationId = customTextMessage.getHeaders().getCorrelationId();
				SourceCallbackContext sourceCallbackContext = sourceCallback.createContext();
				sourceCallbackContext.setCorrelationId(correlationId);	
				Result<String, CustomJmsMessageAttributes> result  =  CustomResultUtil.getResult(customTextMessage);
				logger.info("***onMessage : result is "+ result.getOutput());
				sourceCallback.handle(result, sourceCallbackContext);
			} catch (JMSException e) {	
				e.printStackTrace();
			}
		}
	}
}
