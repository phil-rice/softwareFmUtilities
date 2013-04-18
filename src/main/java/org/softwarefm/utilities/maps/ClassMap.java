package org.softwarefm.utilities.maps;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class ClassMap<V> implements IMutableSimpleMap<Class<?>, V> {

	private final LinkedHashMap<Class<?>, V> map = new LinkedHashMap<Class<?>, V>();

	@Override
	public V get(Class<?> key) {
		for (Entry<Class<?>, V> entry: map.entrySet())
			if (entry.getKey().isAssignableFrom(key))
				return entry.getValue();
		return null;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Class<?> key(int i) {
		return new ArrayList<Class<?>>(map.keySet()).get(i);
	}

	@Override
	public void put(Class<?> key, V value) {
		map.put(key, value);

	}

	@Override
	public void clear() {
		map.clear();
	}

}
