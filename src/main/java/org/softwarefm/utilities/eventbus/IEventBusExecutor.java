package org.softwarefm.utilities.eventbus;

import org.softwarefm.utilities.callbacks.ICallback2;

public interface IEventBusExecutor {
	<Queue extends IQueue, T>void execute(ICallback2<Queue, T> callback, Queue listener, T param);
}
