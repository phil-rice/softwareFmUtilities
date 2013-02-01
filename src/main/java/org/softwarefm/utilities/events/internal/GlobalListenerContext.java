package org.softwarefm.utilities.events.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.softwarefm.utilities.events.IMultipleListenerListListener;

public class GlobalListenerContext extends HashMap<IMultipleListenerListListener<?>, Object> {

	private final List<IMultipleListenerListListener<?>> globalListeners;

	public GlobalListenerContext(List<IMultipleListenerListListener<?>> globalListeners) {
		this.globalListeners = new ArrayList<IMultipleListenerListListener<?>>(globalListeners);
	}

	public List<IMultipleListenerListListener<?>> getGlobalListeners() {
		return globalListeners;
	}
}
