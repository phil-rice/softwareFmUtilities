package org.softwarefm.utilities.eventbus;


public interface IEventBusWithExecutors extends IEventBus {
	<T> void registerExecutor(Class<?> markerClass, IEventBusExecutor executor);

}
