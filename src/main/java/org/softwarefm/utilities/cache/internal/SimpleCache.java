package org.softwarefm.utilities.cache.internal;

import java.util.Map;

import org.softwarefm.utilities.cache.IStaleCacheStrategy;
import org.softwarefm.utilities.collections.Iterables;
import org.softwarefm.utilities.exceptions.WrappedException;
import org.softwarefm.utilities.functions.IFunction1;
import org.softwarefm.utilities.maps.IMutableSimpleMap;
import org.softwarefm.utilities.maps.Maps;

public class SimpleCache<K, V> implements IMutableSimpleMap<K, V> {

	private final IStaleCacheStrategy<K> staleCacheStrategy;
	private final IFunction1<K, V> dataGetter;
	private final Map<K, V> map = Maps.synchronizedLinkedMap();

	public SimpleCache(IStaleCacheStrategy<K> staleCacheStrategy, IFunction1<K, V> dataGetter) {
		this.staleCacheStrategy = staleCacheStrategy;
		this.dataGetter = dataGetter;
	}

	@Override
	public V get(K key) {
		try {
			if (staleCacheStrategy.isStale(key) || !map.containsKey(key)){
				map.put(key, dataGetter.apply(key));
			}
			return map.get(key);
		} catch (Exception e) {
			throw WrappedException.wrap(e);
		}
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public K key(int i) {
		return Iterables.nth(map.keySet(), i);
	}

	@Override
	public void put(K key, V value) {
		map.put(key, value);

	}

	@Override
	public void clear() {
		map.clear();
	}

}
