package org.softwarefm.utilities.events.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.softwarefm.utilities.callbacks.ICallback;
import org.softwarefm.utilities.events.IMultipleListenerListListener;

public class GlobalListenerList {
	final List<IMultipleListenerListListener<?>> globalListeners = Collections.synchronizedList(new ArrayList<IMultipleListenerListListener<?>>());
	private final Object lock = new Object();

	public <Context> void addGlobalListener(IMultipleListenerListListener<Context> listener) {
		synchronized (lock) {
			globalListeners.add(listener);
		}
	}

	public <Context> void removeGlobalListener(IMultipleListenerListListener<Context> listener) {
		synchronized (lock) {
			globalListeners.remove(listener);
		}
	}

	public <L> GlobalListenerContext fireStart(int eventId, Object source, Class<L> clazz, ICallback<L> callback) {
		GlobalListenerContext contexts = makeContext();
		for (IMultipleListenerListListener<?> globalListener : contexts.getGlobalListeners())
			contexts.put(globalListener, globalListener.fireStarted(eventId, source, clazz, callback));
		return contexts;
	}

	private GlobalListenerContext makeContext() {
		synchronized (lock) {
			return new GlobalListenerContext(globalListeners);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <L> void fireEnd(int eventId, GlobalListenerContext contexts, ICallback<L> callback) {
		for (IMultipleListenerListListener globalListener : contexts.getGlobalListeners())
			globalListener.fireEnded(eventId, contexts.get(globalListener));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <L> void fireListenerStart(int eventId, GlobalListenerContext contexts, L listener) {
		for (IMultipleListenerListListener globalListener : contexts.getGlobalListeners())
			globalListener.listenerStarted(eventId, contexts.get(globalListener), listener);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <L> void fireListenerEnd(int eventId, GlobalListenerContext contexts, L listener) {
		for (IMultipleListenerListListener globalListener : contexts.getGlobalListeners())
			globalListener.listenerEnded(eventId, contexts.get(globalListener), listener);
	}

}
