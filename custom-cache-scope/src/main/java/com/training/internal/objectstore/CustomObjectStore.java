package com.training.internal.objectstore;

import java.io.IOException;

import org.mule.runtime.api.store.ObjectStore;
import org.mule.runtime.api.store.ObjectStoreException;
import org.mule.runtime.api.store.ObjectStoreManager;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.training.internal.operations.CustomOperations;
import com.training.internal.parameters.ValueType;

public class CustomObjectStore {

	private static final Logger logger = LoggerFactory.getLogger(CustomOperations.class);

	private ObjectStore<ValueType> objectStore;

	public CustomObjectStore(ObjectStoreManager objectStoreManager) {
		this.objectStore = objectStoreManager.getDefaultPartition();
	}

	public void store(String key, Result result) throws ObjectStoreException, IOException {
		ValueType valueType = CustomObjectStoreUtil.createValueType(result);
		synchronized (objectStore) {
			objectStore.store(key, valueType);
		}
	}

	public Result retrieve(String key) throws ObjectStoreException {
		ValueType valueType = new ValueType();
		synchronized (objectStore) {
			valueType = objectStore.retrieve(key);
		}
		Result result = CustomObjectStoreUtil.getResult(valueType);
		return result;
	}

	public boolean contains(String key) throws ObjectStoreException {
		boolean isExisting = objectStore.contains(key);
		logger.info("key exists? " + isExisting);
		return isExisting;
	}

}
