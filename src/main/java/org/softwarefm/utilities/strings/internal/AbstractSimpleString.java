package org.softwarefm.utilities.strings.internal;

import org.softwarefm.utilities.strings.ISimpleString;

public abstract class AbstractSimpleString implements ISimpleString {
	protected int start;
	protected int length;

	@Override
	public int length() {
		return length;
	}

	public void setLength(int size) {
		this.length = size;
	}

}
