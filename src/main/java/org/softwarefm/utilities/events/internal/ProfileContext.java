package org.softwarefm.utilities.events.internal;

import java.text.MessageFormat;

import org.softwarefm.utilities.callbacks.ICallback;

public class ProfileContext {

	final Object source;
	final Class<?> clazz;
	final long startFireTime;
	long startListenerTime;
	private final String summary;
	private final ICallback<?> fireCallback;

	public ProfileContext(Object source, Class<?> clazz, ICallback<?> fireCallback) {
		this.source = source;
		this.clazz = clazz;
		this.fireCallback = fireCallback;
		this.startFireTime = System.nanoTime();
		this.summary = MessageFormat.format("{0}/{1}/{2}", source, clazz.getSimpleName(), fireCallback);
	}


	public String summary() {
		return summary;
	}

}
