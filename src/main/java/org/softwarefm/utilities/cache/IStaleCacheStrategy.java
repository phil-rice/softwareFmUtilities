package org.softwarefm.utilities.cache;

public interface IStaleCacheStrategy<K> {

	boolean isStale(K key);
}
