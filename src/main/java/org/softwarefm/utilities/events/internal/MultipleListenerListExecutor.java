package org.softwarefm.utilities.events.internal;

import org.softwarefm.utilities.callbacks.ICallback;
import org.softwarefm.utilities.events.IMultipleListenerListExecutor;

public class MultipleListenerListExecutor implements IMultipleListenerListExecutor {

	@Override
	public <L> void execute(int eventId, ICallback<L> callback, L listener, GlobalListenerList globalList, GlobalListenerContext contexts) {
		globalList.fireListenerStart(eventId, contexts, listener);
		ICallback.Utils.call(callback, listener);
		globalList.fireListenerEnd(eventId, contexts, listener);
	}

}
