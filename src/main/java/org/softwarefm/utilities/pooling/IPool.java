package org.softwarefm.utilities.pooling;

import org.softwarefm.utilities.pooling.internal.Pool;

public interface IPool<T> {

	/** please give me an item: make it if there isn't one spare */
	T get(IPoolStore poolStore);

	/** only returns item if it is in use at the moment, exception otherwise */
	T get(int i);

	int inUse();

	/** All the items are available again */
	void reset();

	public static class Utils {
		public static <T> IPool<T> pool(IObjectDefinition<T> objectDefinition) {
			return new Pool<T>(objectDefinition);
		}
	}

}
