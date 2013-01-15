package org.softwarefm.utilities.pooling;

import org.softwarefm.utilities.pooling.internal.PooledMapOfLists;

public interface IMutableMapOfLists<K, V> extends IMapOfLists<K, V> {

	void add(IPoolStore poolStore, K key, V item);

	void clear();

	public static class Utils {
		/** Max 200 keys and max 200 in each list */
		public static <K, V> IMutableMapOfLists<K, V> smallMapOfLists() {
			return mapOfLists(200, 200);
		}

		public static <K, V> IMutableMapOfLists<K, V> mapOfLists(int maxKeys, int maxValues) {
			return new PooledMapOfLists<K, V>(maxKeys, maxValues);
		}
	}

}
