package org.softwarefm.utilities.pooling.internal;

import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;

public class ObjectArrayDefn implements IObjectDefinition<Object[]> {

	private final int size;

	public ObjectArrayDefn(int size) {
		this.size = size;
	}

	@Override
	public Class<Object[]> objectClass() {
		return Object[].class;
	}

	@Override
	public Object[] createBlank(IPoolStore poolStore) {
		return new Object[size];
	}

	@Override
	public void clear(IPoolStore poolStore, Object[] oldObject) {
		for (int i = 0; i < size; i++)
			oldObject[i] = null;
	}

}
