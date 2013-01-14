package org.softwarefm.utilities.pooling.internal;

import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;

public class KeyValueObjectDefinition<K, V> implements IObjectDefinition<KeyValue<K, V>> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Class<KeyValue<K, V>> objectClass() {
		return (Class) KeyValue.class;
	}

	@Override
	public KeyValue<K, V> createBlank(IPoolStore poolStore) {
		return new KeyValue<K, V>();
	}

	@Override
	public void clear(IPoolStore poolStore, KeyValue<K, V> oldObject) {
		oldObject.key = null;
		oldObject.value = null;
	}

}
