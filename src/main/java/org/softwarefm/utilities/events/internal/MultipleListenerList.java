package org.softwarefm.utilities.events.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.softwarefm.utilities.callbacks.ICallback;
import org.softwarefm.utilities.collections.Lists;
import org.softwarefm.utilities.events.IMultipleListenerList;
import org.softwarefm.utilities.events.IValid;
import org.softwarefm.utilities.exceptions.MultipleExceptions;
import org.softwarefm.utilities.maps.Maps;

public class MultipleListenerList implements IMultipleListenerList {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	final Map<Object, Map<Class<?>, List<Object>>> map = Collections.synchronizedMap(new HashMap());
	private final Object lock = new Object();

	public <L> void addListener(Object source, Class<L> clazz, L listener) {
		synchronized (lock) {
			Maps.addToList(map, source, clazz, listener);
		}
	}

	public <L> void removeListener(Object source, Class<L> clazz, L listener) {
		synchronized (lock) {
			Maps.removeFromList(map, source, clazz, listener);
		}
	}

	@SuppressWarnings("unchecked")
	public <L> boolean contains(Object source, java.lang.Class<L> clazz, L listener) {
		List<L> list = (List<L>) Maps.getOrEmptyList(map, source, clazz);
		return list.contains(listener);
	};

	@SuppressWarnings("unchecked")
	public <L> int size(Object source, java.lang.Class<L> clazz) {
		List<L> list = (List<L>) Maps.getOrEmptyList(map, source, clazz);
		return list.size();
	};

	@SuppressWarnings("unchecked")
	public <L> void fire(Object source, Class<L> clazz, ICallback<L> callback) {
		List<Throwable> throwables = new ArrayList<Throwable>();
		List<L> list = (List<L>) Maps.getOrEmptyList(map, source, clazz);
		List<L> toBeRemoved = new ArrayList<L>();
		for (Iterator<L> iterator = Lists.synchronizedCopyOf(list, lock).iterator(); iterator.hasNext();)
			try {
				L listener = iterator.next();
				if (listener instanceof IValid)
					if (((IValid) listener).isValid())
						callback.process(listener);
					else
						toBeRemoved.add(listener);
				else
					callback.process(listener);
			} catch (ThreadDeath e) {
				throw e;
			} catch (Throwable e) {
				throwables.add(e);
			}
		synchronized (lock) {
			list.removeAll(toBeRemoved);
		}
		MultipleExceptions.throwIfNeeded(throwables);
	}

	@Override
	public <L> void clear(Object source, Class<L> clazz) {
		synchronized (lock) {
			Map<Class<?>, List<Object>> map2 = map.get(source);
			if (map2 != null)
				map2.remove(clazz);
		}
	}

}
