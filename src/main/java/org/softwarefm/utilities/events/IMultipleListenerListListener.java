package org.softwarefm.utilities.events;

import org.softwarefm.utilities.callbacks.ICallback;

public interface IMultipleListenerListListener<Context> {

	<L> Context fireStarted(int eventId, Object source, Class<L> clazz, ICallback<L> callback);

	<L> void fireEnded(int eventId, Context context);

	void listenerStarted(int eventId, Context context, Object listener);

	void listenerEnded(int eventId, Context context, Object listener);
}
