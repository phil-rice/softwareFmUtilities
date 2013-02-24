package org.softwarefm.utilities.pooling.internal;

import org.softwarefm.utilities.maps.IMutableSimpleMap;
import org.softwarefm.utilities.maps.MutableSimpleMap;
import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPool;
import org.softwarefm.utilities.pooling.IPoolStore;

public class ThreadSafePoolStore implements IPoolStore {

	private final ThreadLocal<IMutableSimpleMap<IObjectDefinition<?>, IPool<?>>> cache = new ThreadLocal<IMutableSimpleMap<IObjectDefinition<?>, IPool<?>>>() {
		@Override
		protected IMutableSimpleMap<IObjectDefinition<?>, IPool<?>> initialValue() {
			return new MutableSimpleMap<IObjectDefinition<?>, IPool<?>>(100);
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	public <T> IPool<T> pool(IObjectDefinition<T> objectDefinition) {
		IMutableSimpleMap<IObjectDefinition<?>, IPool<?>> map = cache.get();
		IPool<?> pool = map.get(objectDefinition);
		if (pool == null)
			map.put(objectDefinition, pool = IPool.Utils.pool(objectDefinition));
		return (IPool<T>) pool;
	}

	@Override
	public void reset() {
		IMutableSimpleMap<IObjectDefinition<?>, IPool<?>> map = cache.get();
		for (int i = 0; i < map.size(); i++) {
			IObjectDefinition<?> key = map.key(i);
			IPool<?> pool = map.get(key);
			pool.reset();
		}
	}

}
