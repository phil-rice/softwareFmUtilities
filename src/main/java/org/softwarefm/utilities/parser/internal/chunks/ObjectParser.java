package org.softwarefm.utilities.parser.internal.chunks;

import java.util.List;

import org.softwarefm.utilities.parser.IParser;
import org.softwarefm.utilities.parser.internal.ParserState;

/** A composite chunk that just reads in an object */
public class ObjectParser<T> implements IParser {

	private StartObjectChunk<T> startObjectChunk;
	private List<IParser> content;
	private EndObjectParser endObjectParser;

	public ObjectParser(StartObjectChunk<T> startObjectChunk, List<IParser> content, EndObjectParser endObjectParser) {
		this.startObjectChunk = startObjectChunk;
		this.content = content;
		this.endObjectParser = endObjectParser;
	}

	@Override
	public boolean parse(ParserState state) {
		boolean found = false;
		while (startObjectChunk.parse(state)) {
			for (IParser chunk : content)
				chunk.parse(state);
			endObjectParser.parse(state);
			found = true;
		}
		return found;
	}
}
