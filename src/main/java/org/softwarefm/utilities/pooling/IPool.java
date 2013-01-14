package org.softwarefm.utilities.pooling;

import org.softwarefm.utilities.pooling.internal.Pool;


public interface IPool<T> {

	/** please give me an item: make it if there isn't one spare */
	T get(IPoolStore poolStore);

	/** All the items are available again */
	void reset();

	public static class Utils {
		public static <T> IPool<T> pool(IObjectDefinition<T> objectDefinition) {
			return new Pool<T>(objectDefinition);
		}
	}

}
