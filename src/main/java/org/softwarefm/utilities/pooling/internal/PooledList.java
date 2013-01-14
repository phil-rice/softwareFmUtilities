package org.softwarefm.utilities.pooling.internal;

import org.softwarefm.utilities.pooling.IPooledList;

public class PooledList<T> implements IPooledList<T> {

	private Object[] data;
	private int size;

	public PooledList(int maxSize) {
		this.data = new Object[maxSize];
	}

	@Override
	public int size() {
		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index is " + index + " size is " + size);
		return (T) data[index];
	}

	@Override
	public void clear() {
		size = 0;
	}

	public void add(T item) {
		if (size >= data.length)
			throw new IndexOutOfBoundsException("MaxSize is " + data.length + " size is " + size);
		data[size++] = item;
	}
	@Override
	public T pop() {
		return (T) data[--size];
	}
}
