package org.softwarefm.utilities.library;

import org.softwarefm.utilities.strings.ISimpleString;

public class Library {
	public ISimpleString name;

	public Library() {

	}

	public Library(String name) {
		this.name = ISimpleString.Utils.fromUtf8String(name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Library other = (Library) obj;
		return ISimpleString.Utils.equivalent(name, other.name);
	}
}