package org.softwarefm.utilities.parser.internal;

import org.softwarefm.utilities.parser.IParser;
import org.softwarefm.utilities.parser.ParserState;

public class FieldChunkParser implements IParser {

	private StartFieldChunk startFieldChunk;
	private EndFieldParser endFieldParser;

	public FieldChunkParser(StartFieldChunk startFieldChunk, EndFieldParser endFieldParser) {
		this.startFieldChunk = startFieldChunk;
		this.endFieldParser = endFieldParser;
	}

	@Override
	public boolean parse(ParserState state) {
		startFieldChunk.parse(state);
		endFieldParser.parse(state);
		return true;
	}

	@Override
	public String toString() {
		return "FieldChunk [startFieldChunk=" + startFieldChunk + ", endFieldChunk=" + endFieldParser + "]";
	}

}
