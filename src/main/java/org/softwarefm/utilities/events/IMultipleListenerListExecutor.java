package org.softwarefm.utilities.events;

import org.softwarefm.utilities.callbacks.ICallback;
import org.softwarefm.utilities.events.internal.GlobalListenerContext;
import org.softwarefm.utilities.events.internal.GlobalListenerList;
import org.softwarefm.utilities.events.internal.MultipleListenerListExecutor;
import org.softwarefm.utilities.events.internal.NoMultipleListenerListExecutor;

public interface IMultipleListenerListExecutor {

	<L> void execute(int eventId, ICallback<L> callback, L listener, GlobalListenerList globalList, GlobalListenerContext contexts);

	public static class Utils {
		private static NoMultipleListenerListExecutor noMultipleListenerListExecutor = new NoMultipleListenerListExecutor();

		public static IMultipleListenerListExecutor defaultExecutor() {
			return new MultipleListenerListExecutor();
		}

		public static IMultipleListenerListExecutor noExecutor() {
			return noMultipleListenerListExecutor;
		}
	}

}
