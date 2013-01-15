package org.softwarefm.utilities.pooling.internal;

import org.softwarefm.utilities.pooling.IMutableSimpleList;
import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;

public class MutableListObjectDefinition <T>implements IObjectDefinition<IMutableSimpleList<T>> {

	private int maxArrays;

	public MutableListObjectDefinition(int maxArrays) {
		this.maxArrays = maxArrays;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class<IMutableSimpleList<T>> objectClass() {
		return (Class)IMutableSimpleList.class;
	}

	@Override
	public IMutableSimpleList<T> createBlank(IPoolStore poolStore) {
		return IMutableSimpleList.Utils.mutableSimpleList(maxArrays);
	}

	public void clear(IPoolStore poolStore, IMutableSimpleList<T> oldObject) {
		oldObject.clear();
	}

}
