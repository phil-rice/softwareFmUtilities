package org.softwarefm.utilities.library;

import org.softwarefm.utilities.strings.ISimpleString;

public class Book {
	public ISimpleString name;
	public ISimpleString description;

	public Book() {
	}

	public Book(String name, String description) {
		this.name = ISimpleString.Utils.fromUtf8String(name);
		this.description = ISimpleString.Utils.fromUtf8String(description);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		Book other = (Book) obj;
		if (! ISimpleString.Utils.equivalent(name, other.name) )
			return false;
		return ISimpleString.Utils.equivalent(description, other.description);
	}

}