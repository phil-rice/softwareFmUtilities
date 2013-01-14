package org.softwarefm.utilities.pooling.internal;

import org.softwarefm.utilities.collections.ISimpleList;
import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.pooling.IPooledList;
import org.softwarefm.utilities.pooling.IPooledMapOfLists;

/** Very poor speed approach, unless the map is small */
public class PooledMapOfLists<K, V> implements IPooledMapOfLists<K, V> {

	private final IObjectDefinition<KeyValue<K, IPooledList<V>>> keyValueDefn = new KeyValueObjectDefinition<K, IPooledList<V>>();

	private IPooledList<KeyValue<K, IPooledList<V>>> list;

	private IObjectDefinition<IPooledList<V>> pooledListdefn;

	public PooledMapOfLists(int maxKeys, int maxValues) {
		list = IPooledList.Utils.pooledList(maxKeys);
		pooledListdefn = IPooledList.Utils.pooledListdefn(maxValues);
	}

	public void add(IPoolStore poolStore, K key, V item) {
		IPooledList<V> valueList = get(poolStore, key);
		if (valueList == null) {
			KeyValue<K, IPooledList<V>> keyValue = poolStore.pool(keyValueDefn).get(poolStore);
			keyValue.key = key;
			valueList = keyValue.value = poolStore.pool(pooledListdefn).get(poolStore);
			list.add(keyValue);
		}
		valueList.add(item);
	};

	@Override
	public void clear() {
		for (int i = 0; i < list.size(); i++)
			list.get(i).value.clear();
		list.clear();
	}

	@Override
	public IPooledList<V> get(IPoolStore poolStore, K key) {
		for (int i = 0; i < list.size(); i++) {
			KeyValue<K, IPooledList<V>> candidate = list.get(i);
			if (candidate.key == null) {
				if (key == null)
					return candidate.value;
			} else if (candidate.key.equals(key))
				return candidate.value;
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ISimpleList<KeyValue<K, ISimpleList<V>>> entries() {
		return (ISimpleList) list;
	}

}
