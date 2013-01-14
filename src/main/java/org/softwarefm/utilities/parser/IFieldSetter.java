package org.softwarefm.utilities.parser;

import org.softwarefm.utilities.parser.internal.ParserState;

public interface IFieldSetter {

	/** the return value is the new index */
	void setField(ParserState state);

}
