package org.softwarefm.utilities.events;

import org.softwarefm.utilities.callbacks.ICallback;
import org.softwarefm.utilities.events.internal.LoggingListener;
import org.softwarefm.utilities.events.internal.MultipleListenerList;
import org.softwarefm.utilities.events.internal.ProfilingListener;

public interface IMultipleListenerList {

	<Context> void addGlobalListener(IMultipleListenerListListener<Context> listener);

	<Context> void removeGlobalListener(IMultipleListenerListListener<Context> listener);

	void registerExecutor(Class<?> clazz, IMultipleListenerListExecutor executor);

	<L> void addListener(Object source, Class<L> clazz, L listener);

	<L> void removeListener(Object source, Class<L> clazz, L listener);

	<L> boolean contains(Object source, Class<L> clazz, L listener);

	<L> int size(Object source, Class<L> clazz);

	<L> void fire(Object source, Class<L> clazz, ICallback<L> callback);

	<L> void clear(Object source, Class<L> clazz);

	public static class Utils {
		public static IMultipleListenerList defaultList() {
			return new MultipleListenerList();
		}

		public static IMultipleListenerListListener<String> loggingListener(final ICallback<String> callback) {
			return new LoggingListener(callback);

		}

		public static IMultipleListenerListListener<String> sysoutListener(final ICallback<String> callback) {
			return new LoggingListener(ICallback.Utils.<String> sysoutCallback());

		}

		public static IMultipleListenerListListener<?> sysoutProfiler() {
			return new ProfilingListener(ICallback.Utils.<String> sysoutCallback());
		}

		public static IMultipleListenerListListener<?> profiler(ICallback<String> callback) {
			return new ProfilingListener(callback);
		}
	}

}
