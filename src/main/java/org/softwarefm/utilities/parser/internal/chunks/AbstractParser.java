package org.softwarefm.utilities.parser.internal.chunks;

import java.io.UnsupportedEncodingException;

import org.softwarefm.utilities.exceptions.WrappedException;
import org.softwarefm.utilities.parser.IParser;
import org.softwarefm.utilities.parser.internal.ParserState;

public abstract class AbstractParser implements IParser {

	protected String marker;
	private byte[] markerBytes;

	public AbstractParser(String objectMarker, String encoding) {
		try {
			this.marker = objectMarker;
			this.markerBytes = objectMarker.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			throw WrappedException.wrap(e);
		}
	}

	@Override
	public boolean parse(ParserState state) {
		needMarker(state);
		return true;
	}

	protected void needMarker(ParserState state) {
		int index = state.nextIndex;
		if (state.checkMarkers)
			for (int i = 0; i < markerBytes.length; i++) {
				byte b = state.input.byteAt(index++);
				if (markerBytes[i] != b)
					throw new ParserException("Cannot find marker " + marker, state);
			}
		state.nextIndex += markerBytes.length;
	}

	protected boolean optionalMarker(ParserState state) {
		int index = state.nextIndex;
		for (int i = 0; i < markerBytes.length; i++) {
			if (index >= state.input.length())
				return false;
			byte b = state.input.byteAt(index++);
			if (markerBytes[i] != b)
				return false;
		}
		state.nextIndex += markerBytes.length;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [marker=" + marker + "]";
	}
}
