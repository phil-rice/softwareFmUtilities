package org.softwarefm.utilities.parser;

import org.softwarefm.utilities.collections.ISimpleList;

public interface IParseTree {

	<T> T root();
	
	<P, C>ISimpleList<C> children(P parent);
}
