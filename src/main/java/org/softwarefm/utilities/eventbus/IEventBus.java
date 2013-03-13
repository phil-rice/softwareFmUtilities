package org.softwarefm.utilities.eventbus;

import org.softwarefm.utilities.callbacks.ICallback2;
import org.softwarefm.utilities.eventbus.internal.EventBus;

public interface IEventBus {

	/** The object should implement one or more interfaces specified by the queue class */
	void registerListener(Object listener);

	void removeListener(Object listener);

	/** registers a queue with a single parameter, and the way to call implementors */
	<Queue extends IQueue, T> void registerQueue(Class<Queue> queueClass, ICallback2<Queue, T> callback);

	/** tells the listeners that an event has occured with a parameter */
	<Queue extends IQueue, T> void notify(Class<Queue> queue, T param);

	public static class  Utils {
		public static IEventBusWithExecutors eventBus(){
			return new EventBus();
		}
	}
}
