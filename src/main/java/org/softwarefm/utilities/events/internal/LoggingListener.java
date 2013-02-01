package org.softwarefm.utilities.events.internal;

import java.text.MessageFormat;

import org.softwarefm.utilities.callbacks.ICallback;
import org.softwarefm.utilities.events.IMultipleListenerListListener;
import org.softwarefm.utilities.indent.Indent;

public class LoggingListener implements IMultipleListenerListListener<String> {
	private final ICallback<String> callback;
	Indent indent = new Indent();

	public LoggingListener(ICallback<String> callback) {
		this.callback = callback;
	}

	@Override
	public void listenerStarted(int eventId, String context, Object listener) {
		ICallback.Utils.call(callback, MessageFormat.format(indent + "listenerStarted({0}, {1}, {2})", eventId, context, listener));
	}

	@Override
	public void listenerEnded(int eventId, String context, Object listener) {
		ICallback.Utils.call(callback, MessageFormat.format(indent + "listenerEnded({0}, {1}, {2})", eventId, context, listener));
	}

	@Override
	public <L> String fireStarted(int eventId, Object source, Class<L> clazz, ICallback<L> fireCallback) {
		String context = MessageFormat.format("{0}, {1}, {2}", source, clazz, fireCallback);
		ICallback.Utils.call(callback, MessageFormat.format(indent + "fireStarted({0}, {1})", eventId, context));
		indent = indent.indent();
		return context;
	}

	@Override
	public <L> void fireEnded(int eventId, String context) {
		indent = indent.unindent();
		ICallback.Utils.call(callback, MessageFormat.format(indent + "fireEnded({0}, {1})", eventId, context));
	}
}