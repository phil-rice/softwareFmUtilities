package org.softwarefm.utilities.pooling.internal;

import org.softwarefm.utilities.collections.ISimpleList;
import org.softwarefm.utilities.pooling.IMutableMapOfLists;
import org.softwarefm.utilities.pooling.IMutableSimpleList;
import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;

/** Very poor speed approach, unless the map is small */
public class PooledMapOfLists<K, V> implements IMutableMapOfLists<K, V> {

	private final IObjectDefinition<KeyValue<K, IMutableSimpleList<V>>> keyValueDefn = new KeyValueObjectDefinition<K, IMutableSimpleList<V>>();

	private final IMutableSimpleList<KeyValue<K, IMutableSimpleList<V>>> list;

	private final IObjectDefinition<IMutableSimpleList<V>> pooledListdefn;

	public PooledMapOfLists(int maxKeys, int maxValues) {
		list = IMutableSimpleList.Utils.mutableSimpleList(maxKeys);
		pooledListdefn = IMutableSimpleList.Utils.pooledListdefn(maxValues);
	}

	public void add(IPoolStore poolStore, K key, V item) {
		IMutableSimpleList<V> valueList = get(poolStore, key);
		if (valueList == null) {
			KeyValue<K, IMutableSimpleList<V>> keyValue = poolStore.pool(keyValueDefn).get(poolStore);
			keyValue.key = key;
			valueList = keyValue.value = poolStore.pool(pooledListdefn).get(poolStore);
			list.add(keyValue);
		}
		valueList.add(item);
	}

	@Override
	public void clear() {
		for (int i = 0; i < list.size(); i++)
			list.get(i).value.clear();
		list.clear();
	}

	@Override
	public IMutableSimpleList<V> get(IPoolStore poolStore, K key) {
		for (int i = 0; i < list.size(); i++) {
			KeyValue<K, IMutableSimpleList<V>> candidate = list.get(i);
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
