package org.softwarefm.utilities.pooling;

import org.softwarefm.utilities.collections.ISimpleList;
import org.softwarefm.utilities.pooling.internal.KeyValue;

public interface IMapOfLists<K,V> {
	/** can return null if not in */
	IPooledList<V> get(IPoolStore poolStore, K key);

	ISimpleList<KeyValue<K, ISimpleList<V>>> entries();
}
