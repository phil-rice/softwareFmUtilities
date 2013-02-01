package org.softwarefm.utilities.events.internal;

import org.softwarefm.utilities.events.internal.ListenerListTest.ITestListener;

class TestListener implements ITestListener {

	private final String name;

	public TestListener(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}