package org.softwarefm.utilities.pooling;

import org.softwarefm.utilities.collections.ISimpleList;

public interface IPooledList<T> extends ISimpleList<T> {

	void add(T item);

	void clear();
	
	T pop();
}
