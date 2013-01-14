package org.softwarefm.utilities.parser.internal.chunks;

import org.softwarefm.utilities.parser.internal.ParserState;

public class EndObjectParser extends AbstractParser {

	public EndObjectParser(String objectMarker, String encoding) {
		super(objectMarker, encoding);
	}

	@Override
	public boolean parse(ParserState state) {
		needMarker(state);
		state.parents.pop();
		return true;
	}

}