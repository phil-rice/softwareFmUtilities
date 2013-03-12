package org.softwarefm.utilities.events.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.softwarefm.utilities.callbacks.ICallback;
import org.softwarefm.utilities.collections.Lists;
import org.softwarefm.utilities.events.IMultipleListenerList;
import org.softwarefm.utilities.events.IMultipleListenerListExecutor;
import org.softwarefm.utilities.events.IMultipleListenerListListener;
import org.softwarefm.utilities.events.IValid;
import org.softwarefm.utilities.exceptions.MultipleExceptions;
import org.softwarefm.utilities.maps.Maps;

public class MultipleListenerList implements IMultipleListenerList {

	private final Object lock = new Object();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	final Map<Object, Map<Class<?>, List<Object>>> map = Collections.synchronizedMap(new HashMap());
	final GlobalListenerList globalListeners = new GlobalListenerList();

	private final IMultipleListenerListExecutor noExecutor = IMultipleListenerListExecutor.Utils.noExecutor();
	private final IMultipleListenerListExecutor defaultExecutor = IMultipleListenerListExecutor.Utils.defaultExecutor();
	private int nextEventId;
	private final Map<Class<?>, IMultipleListenerListExecutor> executors = Collections.synchronizedMap(Maps.<Class<?>, IMultipleListenerListExecutor> newMap());

	@Override
	public <Context> void addGlobalListener(IMultipleListenerListListener<Context> listener) {
		globalListeners.addGlobalListener(listener);
	}

	@Override
	public <Context> void removeGlobalListener(IMultipleListenerListListener<Context> listener) {
		globalListeners.removeGlobalListener(listener);
	}

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
	}

	@SuppressWarnings("unchecked")
	public <L> int size(Object source, java.lang.Class<L> clazz) {
		List<L> list = (List<L>) Maps.getOrEmptyList(map, source, clazz);
		return list.size();
	}

	public <L> void fire(Object source, Class<L> clazz, ICallback<L> callback) {
		int eventId = nextEventId++;
		List<Throwable> throwables = new ArrayList<Throwable>();
		GlobalListenerContext contexts = globalListeners.fireStart(eventId, source, clazz, callback);
		try {
			List<L> toBeRemoved = new ArrayList<L>();
			List<L> listeners = getListeners(source, clazz);
			for (L listener : listeners)
				try {
					IMultipleListenerListExecutor executor = findExecutorAndRemoveIfNeeded(toBeRemoved, callback, listener);
					executor.execute(eventId, callback, listener, globalListeners, contexts);
				} catch (ThreadDeath e) {
					throw e;
				} catch (Throwable e) {
					throwables.add(e);
				}

			Lists.synchronizedRemoveAll(listeners, toBeRemoved, lock);
			MultipleExceptions.throwIfNeeded(throwables);
		} finally {
			globalListeners.fireEnd(eventId, contexts, callback);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <L> List<L> getListeners(Object source, Class<L> clazz) {
		synchronized (lock) {
			return (List) Maps.getOrEmptyList(map, source, clazz);
		}
	}

	private <L> IMultipleListenerListExecutor findExecutorAndRemoveIfNeeded(List<L> toBeRemoved, ICallback<L> callback, L listener) throws Exception {
		if (listener instanceof IValid)
			if (!((IValid) listener).isValid()) {
				toBeRemoved.add(listener);
				return noExecutor;
			}
		for (Entry<Class<?>, IMultipleListenerListExecutor> entry : executors.entrySet())
			if (entry.getKey().isAssignableFrom(listener.getClass()))
				return entry.getValue();
		return defaultExecutor;
	}

	@Override
	public <L> void clear(Object source, Class<L> clazz) {
		synchronized (lock) {
			Map<Class<?>, List<Object>> map2 = map.get(source);
			if (map2 != null)
				map2.remove(clazz);
		}
	}

	@Override
	public void registerExecutor(Class<?> clazz, IMultipleListenerListExecutor executor) {
		executors.put(clazz, executor);
	}

	@Override
	public String toString() {
		return "MultipleListenerList [globalListeners=" + globalListeners + "]";
	}

}
