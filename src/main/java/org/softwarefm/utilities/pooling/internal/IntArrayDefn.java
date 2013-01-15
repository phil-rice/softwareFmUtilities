package org.softwarefm.utilities.pooling.internal;

import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;

public class IntArrayDefn implements IObjectDefinition<int[]> {

	private final int size;

	public IntArrayDefn(int size) {
		this.size = size;
	}

	@Override
	public Class<int[]> objectClass() {
		return int[].class;
	}

	@Override
	public int[] createBlank(IPoolStore poolStore) {
		return new int[size];
	}

	@Override
	public void clear(IPoolStore poolStore, int[] oldObject) {
		//deliberate decision to ignore old content...it may arrive dirty
	}

}
