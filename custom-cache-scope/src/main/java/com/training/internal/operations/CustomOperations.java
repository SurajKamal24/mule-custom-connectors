package com.training.internal.operations;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.mule.runtime.api.lifecycle.Initialisable;
import org.mule.runtime.api.lifecycle.InitialisationException;
import org.mule.runtime.api.store.ObjectStoreException;
import org.mule.runtime.api.store.ObjectStoreManager;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.process.CompletionCallback;
import org.mule.runtime.extension.api.runtime.route.Chain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.training.internal.objectstore.CustomObjectStore;
import com.training.internal.parameters.CachingParameter;

public class CustomOperations implements Initialisable {

	private static final Logger logger = LoggerFactory.getLogger(CustomOperations.class);

	@Inject
	private ObjectStoreManager objectStoreManager;
	private CustomObjectStore customObjectStore;

	@Override
	public void initialise() throws InitialisationException {
		customObjectStore = new CustomObjectStore(objectStoreManager);

	}

	public void customCache(@ParameterGroup(name = "Caching Configuration") CachingParameter cachingParameter,
			CompletionCallback<Object, Object> callback, Chain chain) {
		String key = cachingParameter.getKey();
		try {
			if (customObjectStore.contains(key)) {
				Result result = customObjectStore.retrieve(key);
				callback.success(result);
			} else {
				Consumer<Result> onSuccess = result -> {
					try {
						customObjectStore.store(key, result);
						callback.success(result);
					} catch (ObjectStoreException | IOException e) {
						e.printStackTrace();
					}
				};
				BiConsumer<Throwable, Result> onError = (error, result) -> callback.error(error);
				chain.process(onSuccess, onError);
			}
		} catch (ObjectStoreException e) {
			e.printStackTrace();
		}
	}

}
