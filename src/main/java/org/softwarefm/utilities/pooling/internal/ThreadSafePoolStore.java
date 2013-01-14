package org.softwarefm.utilities.pooling.internal;

import java.util.HashMap;
import java.util.Map;

import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPool;
import org.softwarefm.utilities.pooling.IPoolStore;

public class ThreadSafePoolStore implements IPoolStore {

	private ThreadLocal<Map<IObjectDefinition<?>, IPool<?>>> cache = new ThreadLocal<Map<IObjectDefinition<?>, IPool<?>>>() {
		protected Map<IObjectDefinition<?>, IPool<?>> initialValue() {
			return new HashMap<IObjectDefinition<?>, IPool<?>>();
		};
	};

	@SuppressWarnings("unchecked")
	@Override
	public <T> IPool<T> pool(IObjectDefinition<T> objectDefinition) {
		Map<IObjectDefinition<?>, IPool<?>> map = cache.get();
		if (!map.containsKey(objectDefinition))
			map.put(objectDefinition, IPool.Utils.pool(objectDefinition));
		return (IPool<T>) map.get(objectDefinition);
	}

}
