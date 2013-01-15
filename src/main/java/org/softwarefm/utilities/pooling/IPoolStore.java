package org.softwarefm.utilities.pooling;

import org.softwarefm.utilities.pooling.internal.ThreadSafePoolStore;

public interface IPoolStore {

	<T> IPool<T> pool(IObjectDefinition<T> objectDefinition);

	void reset();

	public static class Utils {
		public static <T> IPoolStore threadSafePoolStore() {
			return new ThreadSafePoolStore();
		}
	}

}
