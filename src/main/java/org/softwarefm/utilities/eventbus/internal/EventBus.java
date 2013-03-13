package org.softwarefm.utilities.eventbus.internal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.softwarefm.utilities.callbacks.ICallback2;
import org.softwarefm.utilities.eventbus.IEventBusExecutor;
import org.softwarefm.utilities.eventbus.IEventBusWithExecutors;
import org.softwarefm.utilities.eventbus.IQueue;
import org.softwarefm.utilities.maps.Maps;

public class EventBus implements IEventBusWithExecutors {
	private final static IEventBusExecutor defaultExecutor = new EventBusSimpleExecutor();
	private final List<Object> listeners = new ArrayList<Object>();
	private final Map<Class<? extends IQueue>, ICallback2<?, ?>> queues = Maps.newMap();
	private final Map<Class<?>, IEventBusExecutor> executors = Maps.newMap(LinkedHashMap.class);
	private final Map<Object, IEventBusExecutor> executorCache = Maps.newMap();

	private final Object lock = new Object();
	private boolean notifying;

	@Override
	public void removeListener(Object listener) {
		synchronized (lock) {
			checkNotNotifying();
			listeners.remove(listener);
		}
	}

	@Override
	public void registerListener(Object listener) {
		synchronized (lock) {
			checkNotNotifying();
			listeners.add(listener);
		}
	}

	@Override
	public <Queue extends IQueue, T> void registerQueue(Class<Queue> queueClass, ICallback2<Queue, T> callback) {
		synchronized (lock) {
			checkNotNotifying();
			Maps.putNoDuplicates(queues, queueClass, callback);
		}
	}

	private void checkNotNotifying() {
		if (notifying)
			throw new CannotChangeEventBusWhileNotifyingException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Queue extends IQueue, T> void notify(Class<Queue> queue, T param) {
		notifying = true;
		ICallback2<Queue, T> callback;
		ArrayList<Object> listenerCopy;

		synchronized (lock) {
			callback = (ICallback2<Queue, T>) queues.get(queue);
			listenerCopy = new ArrayList<Object>(listeners);
		}

		try {
			for (Object listener : listenerCopy)
				if (queue.isAssignableFrom(listener.getClass())) {
					IEventBusExecutor executor = findExecutor(listener);
					executor.execute(callback, (Queue) listener, param);
				}
		} finally {
			notifying = false;
		}
	}

	private IEventBusExecutor findExecutor(final Object listener) {
		synchronized (lock) {
			return Maps.findOrCreate(executorCache, listener, new Callable<IEventBusExecutor>() {
				@Override
				public IEventBusExecutor call() throws Exception {
					for (Entry<Class<?>, IEventBusExecutor> entry : executors.entrySet()) {
						if (entry.getKey().isAssignableFrom(listener.getClass()))
							return entry.getValue();
					}
					return defaultExecutor;
				}
			});
		}
	}

	@Override
	public <T> void registerExecutor(Class<?> markerClass, IEventBusExecutor executor) {
		synchronized (lock) {
			checkNotNotifying();
			Maps.putNoDuplicates(executors, markerClass, executor);
		}
	}

}
