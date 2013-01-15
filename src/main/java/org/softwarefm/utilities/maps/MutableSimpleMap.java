package org.softwarefm.utilities.maps;


public class MutableSimpleMap<K, V> implements IMutableSimpleMap<K, V> {

	private Object[] keys;
	private Object[] values;
	private int index = 0;

	public MutableSimpleMap(int maxSize) {
		keys = new Object[maxSize];
		values = new Object[maxSize];
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) {
		for (int i = 0; i < index; i++)
			if (key.equals(keys[i]))
				return (V) values[i];
		return null;
	}

	@Override
	public int size() {
		return index;
	}

	@SuppressWarnings("unchecked")
	@Override
	public K key(int i) {
		return (K) keys[i];
	}

	@Override
	public void put(K key, V value) {
		keys[index] = key;
		values[index++] = value;
	}

	@Override
	public void clear() {
		index = 0;
	}


}
