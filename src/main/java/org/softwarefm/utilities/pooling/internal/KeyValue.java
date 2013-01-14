package org.softwarefm.utilities.pooling.internal;

public class KeyValue<K, V> {
	public K key;
	public V value;

	public KeyValue() {
		super();
	}

	@Override
	public String toString() {
		return "KeyValue [key=" + key + ", value=" + value + "]";
	}

}
