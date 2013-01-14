package org.softwarefm.utilities.strings;

public interface ISettableSimpleString extends ISimpleString{

	void populate(byte[] bytes, int start, int length);
	
}
