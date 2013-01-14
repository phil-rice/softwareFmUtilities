package org.softwarefm.utilities.exceptions;

import java.util.List;

public class MultipleExceptions extends RuntimeException {

	private final List<Throwable> throwables;

	public MultipleExceptions(List<Throwable> throwables) {
		this.throwables = throwables;
	}

	public List<Throwable> getCauses() {
		return throwables;
	}

	public static void throwIfNeeded(List<Throwable> throwables) {
		switch (throwables.size()) {
		case 1:
			throw WrappedException.wrap(throwables.get(0));
		case 0:
			break;
		default:
			throw new MultipleExceptions(throwables);
		}
	}

	public static void add(List<Throwable> throwables, Throwable e) {
		if (e instanceof ThreadDeath)
			throw (ThreadDeath) e;
		throwables.add(e);
	}
}
