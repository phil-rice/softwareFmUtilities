package org.softwarefm.utilities.eventbus.internal;

import org.softwarefm.utilities.callbacks.ICallback2;
import org.softwarefm.utilities.eventbus.IEventBusExecutor;
import org.softwarefm.utilities.eventbus.IQueue;

public class EventBusSimpleExecutor implements IEventBusExecutor {

	@Override
	public <Queue extends IQueue, T> void execute(ICallback2<Queue, T> callback, Queue listener, T param) {
		ICallback2.Utils.call(callback, listener, param);
	}

}
