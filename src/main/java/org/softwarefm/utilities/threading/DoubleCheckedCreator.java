package org.softwarefm.utilities.threading;

import java.util.concurrent.Callable;

import org.softwarefm.utilities.exceptions.WrappedException;
import org.softwarefm.utilities.lifecycle.IDispose;

public class DoubleCheckedCreator<T> implements IDispose {

	private final Object lock;
	private final Callable<T> creator;
	private T data;

	public DoubleCheckedCreator(Object lock, Callable<T> creator) {
		this.lock = lock;
		this.creator = creator;
	}

	public T get() {
		try {
			if (data == null)
				synchronized (lock) {
					if (data == null)
						data = creator.call();
				}
			return data;
		} catch (Exception e) {
			throw WrappedException.wrap(e);
		}
	}

	@Override
	public void dispose() {
		if (data instanceof IDispose)
			((IDispose) data).dispose();
		this.data = null;
	}

}
