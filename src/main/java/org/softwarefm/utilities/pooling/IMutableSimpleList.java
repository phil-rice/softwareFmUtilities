package org.softwarefm.utilities.pooling;

import org.softwarefm.utilities.collections.ISimpleList;

public interface IMutableSimpleList<T> extends ISimpleList<T> {

	void add(T item);

	void clear();
	
	T pop();
}
