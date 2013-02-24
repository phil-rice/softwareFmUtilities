package org.softwarefm.utilities.pooling.internal;

import java.util.ArrayList;

import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPool;
import org.softwarefm.utilities.pooling.IPoolStore;

public class Pool<T> implements IPool<T> {

	private final IObjectDefinition<T> objectDefinition;
	private final ArrayList<T> objects = new ArrayList<T>();
	private int index;

	public Pool(IObjectDefinition<T> objectDefinition) {
		this.objectDefinition = objectDefinition;
	}

	@Override
	public T get(IPoolStore poolStore) {
		T item = index < objects.size() ? objects.get(index) : null;
		if (item == null) {
			item = objectDefinition.createBlank(poolStore);
			objects.add(item);
		} else
			objectDefinition.clear(poolStore, item);
		index++;
		return item;
	}

	@SuppressWarnings("cast")
	@Override
	public T get(int i) {
		if (i >= index)
			throw new IndexOutOfBoundsException("I is " + i + " index is " + index);
		return (T) objects.get(i);
	}

	@Override
	public void reset() {
		index = 0;
	}
	@Override
	public int inUse() {
		return index;
	}
}
