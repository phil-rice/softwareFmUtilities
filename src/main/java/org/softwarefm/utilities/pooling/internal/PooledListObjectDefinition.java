package org.softwarefm.utilities.pooling.internal;

import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.pooling.IPooledList;

public class PooledListObjectDefinition <T>implements IObjectDefinition<IPooledList<T>> {

	private int maxArrays;

	public PooledListObjectDefinition(int maxArrays) {
		this.maxArrays = maxArrays;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class<IPooledList<T>> objectClass() {
		return (Class)IPooledList.class;
	}

	@Override
	public IPooledList<T> createBlank(IPoolStore poolStore) {
		return IPooledList.Utils.pooledList(maxArrays);
	}

	public void clear(IPoolStore poolStore, IPooledList<T> oldObject) {
		oldObject.clear();
	}

}
