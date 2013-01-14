package org.softwarefm.utilities.parser.internal.chunks;

import org.softwarefm.utilities.parser.internal.ParserState;

public class ParserException extends RuntimeException {

	public ParserException(String message, ParserState state) {
		super(message + " at index " + state.nextIndex + " " + text(state));
	}

	private static String text(ParserState state) {
		int start = Math.max(0, state.nextIndex - 10);
		return state.input.subString(state.poolStore, start, Math.min(10, state.nextIndex)).asString("UTF-8") +"^^^" + state.input.subString(state.poolStore, state.nextIndex, 10).asString("UTF-8");
	}

}
