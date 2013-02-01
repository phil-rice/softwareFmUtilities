package org.softwarefm.utilities.events.internal;

import org.softwarefm.utilities.callbacks.ICallback;
import org.softwarefm.utilities.events.IMultipleListenerListListener;

public class ProfilingListener implements IMultipleListenerListListener<ProfileContext> {
	private final ICallback<String> callback;

	public ProfilingListener(ICallback<String> callback) {
		this.callback = callback;
	}

	@Override
	public <L> ProfileContext fireStarted(int eventId, Object source, Class<L> clazz, ICallback<L> fireCallback) {
		ProfileContext context = new ProfileContext(source, clazz, fireCallback);
		ICallback.Utils.call(callback, "Firing  (" + eventId + ") " + context.summary());
		return context;
	}

	@Override
	public void listenerStarted(int eventId, ProfileContext context, Object listener) {
		context.startListenerTime = System.nanoTime();
	}

	@Override
	public void listenerEnded(int eventId, ProfileContext context, Object listener) {
		long duration = System.nanoTime() - context.startListenerTime;
		ICallback.Utils.call(callback, String.format("         %-5s %7.2fms %s %s", eventId, duration / 1000000.0, context.summary(), listener));
	}

	@Override
	public <L> void fireEnded(int eventId, ProfileContext context) {
		long duration = System.nanoTime() - context.startFireTime;
		ICallback.Utils.call(callback, String.format("Finished %-5s %7.2fms %s", eventId, duration / 1000000.0, context.summary()));
	}
}